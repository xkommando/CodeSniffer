package codesniffer.hash


import codesniffer.core.{CharacVec, X}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag
import scala.util.Random
import X.applyConversion

/**
 * Created by Bowen Cai on 4/25/2015.
 */
// PRNearNeighborStructT
class LSHTable(val param: HashParam, val random: Random, nPointsEstimate: Int) {

  type ULSHVec = (Array[Double], Double) // e.g., the a (vector of real numbers) and b (a real number [0, w])
  //-----------------------------------------------------------------
  //      initializePRNearNeighborFields

  val nHFTuples = param.paramM
  val hfTupleLen = param.paramK / 2

//  val points = new mutable.HashMap[Int, Point]

  //-----------------------------------------------------------------
  //    fill hashedBuckets

  private val _hashedBuckets : Array[UHashStruct] = {
    val mainH = (0 until param.paramK).map(_=> random.nextInt(1, D.MAX_HASH_RND)).toArray
    val controlH = (0 until param.paramK).map(_=> random.nextInt(1, D.MAX_HASH_RND)).toArray

    (0 until param.paramL).map{_ =>
      new UHashStruct(
        new Array[GBucket](nPointsEstimate),
        new Array[Point](nPointsEstimate),
        D.UH_PRIME_DEFAULT,
        mainH,
        controlH)
    }.toArray
  }

  /**
   * 1. initHashFunctions: generate a,b for hash function. Note that 0 <= b < w instead of 0 <= b <= w
   */
  val pULSHVecs: Array[Array[ULSHVec]] = {
    val generateULSHFunc = // generate (a, b)
      ((0 until param.dimension).map(_=>random.nextGaussian()).toArray, random.nextDouble() * param.paramW)
    // generate one row of (a,b)
    val gRow = (0 until hfTupleLen).map(_=>generateULSHFunc).toArray

    // generate nHFTuples rows of (a,b), totally nHFTuples * hfTupleLen pairs of (a,b)
    (0 until nHFTuples).map(_=>gRow).toArray
  }

  /**
   * 2. init fields that are used only in operations ("temporary" variables for operations).
   */
  // row * col = nHFTuples * hfTuplesLength
  val _pointULSHVecs:Array[Array[Int]] = (0 until nHFTuples).map(_=> new Array[Int](hfTupleLen)).toArray

  // row * col = nHFTuples * N_PRECOMPUTED_HASHES_NEEDED (4)
  val _precomputedHashesOfULSHs: Array[Array[Int]] =
    (0 until nHFTuples).map(_=> new Array[Int](D.N_PRECOMPUTED_HASHES_NEEDED)).toArray

  // length: dimension
  private val _reducedPoint: Array[Double] = new Array[Double](param.dimension)

  private val _sizeMarkedPoints = nPointsEstimate
  private val _markedPoints = new Array[Boolean](_sizeMarkedPoints)
  private val _markedPointsIndeces = new Array[Int](_sizeMarkedPoints)

  private def prepareAdd(vec: CharacVec[_]): Unit = {
    val arr = vec.intern
    for (i <- 0 until _reducedPoint.length)
      _reducedPoint(i) = arr(i) / param.paramR
    for (i <- 0 until nHFTuples) {
      for (j <- 0 until hfTupleLen) {
        _pointULSHVecs(i)(j) = computeULSH(_reducedPoint, pULSHVecs(i)(j))
      }
      _precomputedHashesOfULSHs(i)(0) =
        X.computeProductModePrime(_hashedBuckets(0).mainHashA, _pointULSHVecs(i), hfTupleLen)
      _precomputedHashesOfULSHs(i)(0) =
        X.computeProductModePrime(_hashedBuckets(0).controlHash1, _pointULSHVecs(i), hfTupleLen)
      _precomputedHashesOfULSHs(i)(0) =
        X.computeProductModePrime(_hashedBuckets(0).mainHashA.drop(hfTupleLen), _pointULSHVecs(i), hfTupleLen)
      _precomputedHashesOfULSHs(i)(0) =
        X.computeProductModePrime(_hashedBuckets(0).controlHash1.drop(hfTupleLen), _pointULSHVecs(i), hfTupleLen)
    }
  }

  //-----------------------------------------------------------------
  //
  def add(vec: CharacVec[_]): Unit = {

    prepareAdd(vec)

    // get pointIdx

    var firstUComp = 0
    var secondUComp = 1

    val pointIndex: Int = ???

    for(i <- 0 until param.paramL) {
      // add
      this._hashedBuckets(i).addBucketEntry(
        _precomputedHashesOfULSHs(firstUComp),
        _precomputedHashesOfULSHs(secondUComp), pointIndex)

      secondUComp += 1
      if (secondUComp == nHFTuples) {
        firstUComp += 1
        secondUComp = firstUComp + 1
      }
    }
    // Check whether the vectors <nearPoints> & <nearPointsIndeces> is still big enough.
  }


  @inline
  def computeULSH(vector: Array[Double], uLSHVec: ULSHVec): Int = {
     // (vec * a + b) / w
    var sum = 0.0
    for(i <- 0 until param.dimension)
      sum += vector(i) * uLSHVec._1(i)
    ((sum + uLSHVec._2 ) / param.paramW).toFloat.toInt
  }

}
