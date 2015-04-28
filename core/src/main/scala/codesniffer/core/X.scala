package codesniffer.core

import codesniffer.hash.{D, Session}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * Created by Bowen Cai on 4/25/2015.
 */
object X {

  import StrictMath._

  import MathDef._
  import org.apache.commons.math3.special._

  @inline
  def EuclideanDist(arr1: Array[Int], arr2: Array[Int], length: Int): Double = {
    var sum = 0.0
    for (i <- 0 until length) {
      val p = arr1(i)
      val dist = arr2(i) - p
      sum += dist * dist
    }
    java.lang.Math.sqrt(sum)
  }

//  RealT computeFunctionP(RealT w, RealT c) {
  //  RealT x = w / c;
  //  return 1 - ERFC(x / M_SQRT2) - M_2_SQRTPI / M_SQRT2 / x * (1 - EXP(-SQR(x) / 2));
  @inline
  def computeP(w: Double, c: Double): Double = {
    val x = w / c
    val _1 = Erf.erfc( x / M_SQRT2)
    val _2 = M_2_SQRTPI / M_SQRT2 / x
    val _3 = 1 - exp( - x * x/ 2 )
    1 - _1 - _2 * _3
  }

//  IntT computeMForULSH(IntT k, RealT successProbability) {
  //  ASSERT((k & 1) == 0); // k should be even in order to use ULSH.
  //  RealT mu = 1 - POW(computeFunctionP(PARAMETER_W_DEFAULT, 1), k / 2);
  //  RealT P = successProbability;
  //  RealT d = (1 - mu) / (1 - P) * 1 / LOG(1 / mu) * POW(mu, -1 / (1 - mu));
  //  RealT y = LOG(d);
  //  IntT m = CEIL(1 - y / LOG(mu) - 1 / (1 - mu));
  //  while (POW(mu, m - 1) * (1 + m * (1 - mu)) > 1 - P) {
  //    m++;
  //  }
  //  return m;
  @inline
  def computeMfromK_P(k: Int, successProb: Double): Int = {
    val mu = 1 - pow(computeP(D.W_default, 1), k / 2)
    val p = successProb
    val d = (1 - mu) / (1 - p) * 1 / log(1 / mu) * pow(mu, -1 / (1 - mu))
    val y = log(d)
    var m = ceil(1 - y / log(mu)  -  1 / (1 - mu)).toInt
    while (pow(mu, m - 1) * (1 + m * (1 - mu)) > 1 - p)
      m += 1
    m
  }


  // Computes (a.b) mod UH_PRIME_DEFAULT.
//  inline Uns32T computeProductModDefaultPrime(Uns32T *a, Uns32T *b, IntT size){
  @inline
  def computeProductModePrime(a1: Array[Int], a2: Array[Int], length: Int): Int = {
      var h = 0L
      for (i <- 0 until length) {
        h += a1(i) * a2(i)
        h = (h & D.TWO_TO_32_MINUS_1) + 5 * (h >>> 32)
        if (h >= D.UH_PRIME_DEFAULT)
          h -= D.UH_PRIME_DEFAULT
      }
      h.toInt
  }



  // determineRTCoefficients
  def estimate3Time(session: Session): (Double, Double, Double) = {
    val vectorCount = session.vectors.length
    var _sampleSize = vectorCount / 50
    _sampleSize = if (_sampleSize < 100) vectorCount
    else  if (_sampleSize > 10000) 10000 else _sampleSize

    val sample = new ArrayBuffer[CharacVec[_]](_sampleSize)
    for (i <- session.random.shuffle((0 until session.vectors.length).toList).take(_sampleSize)) {
      sample += session.vectors(i)
    }

    val paramM = computeMfromK_P(D.K_default, session.successProb)
    val paramL = paramM * (paramM - 1) / 2


//    val samples = session.vectors.

    ???
  }


  class RandomExt(private[this] val rand: java.util.Random) {

    @inline
    def nextInt(lowerBound: Int, upperBound: Int): Int = rand.nextInt(upperBound - lowerBound + 1) + lowerBound

    @inline
    def nextDouble(lowerBound: Double, upperBound: Double): Double = rand.nextDouble() * (upperBound - lowerBound) + lowerBound

  }

  import scala.language.implicitConversions

  implicit def applyConversion(r: Random) = new RandomExt(r.self)


}
