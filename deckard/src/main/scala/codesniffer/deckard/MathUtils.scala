package codesniffer.deckard

import codesniffer.deckard.hash.{Session, Defs}
import org.apache.commons.math3.special.Erf

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * Created by Bowen Cai on 4/25/2015.
 */
object MathUtils {

  import MathDef._

  /**
   * for vectors with significant length difference
   */
  @inline
  def EuclideanDist[T:Numeric](arr1: Array[T],  len1: Int, arr2: Array[T], len2: Int): Double = {
    var sum = 0.0
    var idx = 0
    var short = 0
    var long = 0
    var sa: Array[T] = null
    var la: Array[T] = null
    if (len1 < len2) {
      sa = arr1
      la = arr2
      short = len1
      long = len2
    } else {
      sa = arr2
      la = arr1
      short = len2
      long = len1
    }
    for (i <- 0 until short) {
      val p = sa(i)
      val dist = implicitly[Numeric[T]] toDouble  (implicitly[Numeric[T]] minus (la(i), p))
      sum += dist * dist
      idx += 1
    }
    for (i <- idx until long) {
      val p = implicitly[Numeric[T]] toDouble (la(i))
      sum += p * p
    }
    StrictMath.sqrt(sum)
  }

  @inline
  def EuclideanDist(arr1: Array[Int], arr2: Array[Int], length: Int): Double = {
    var sum = 0.0
    for (i <- 0 until length) {
      val p = arr1(i)
      val dist = arr2(i) - p
      sum += dist * dist
    }
    StrictMath.sqrt(sum)
  }

  @inline
  def EuclideanDist[T:Numeric](arr1: Array[T], arr2: Array[T], length: Int): Double = {
    var sum = 0.0
    for (i <- 0 until length) {
      val p = arr1(i)
      val dist = implicitly[Numeric[T]] toDouble  (implicitly[Numeric[T]] minus (arr2(i), p))
      sum += dist * dist
    }
    StrictMath.sqrt(sum)
  }

  @inline
  def CosineDist[T:Numeric](arr1: Array[T], arr2: Array[T], length: Int): Double = {
    var sum1 = 0.0
    var sum2 = 0.0
    var prodsum = 0.0
    for (i <- 0 until length) {
      val p1 = implicitly[Numeric[T]] toDouble arr1(i)
      sum1 += p1 * p1
      val p2 = implicitly[Numeric[T]] toDouble arr2(i)
      sum2 += p2 * p2
      prodsum += p1 * p2
    }
    val sqs1 = StrictMath.sqrt(sum1)
    val sqs2 = StrictMath.sqrt(sum2)
    prodsum / sqs1 / sqs2
  }

//  RealT computeFunctionP(RealT w, RealT c) {
  //  RealT x = w / c;
  //  return 1 - ERFC(x / M_SQRT2) - M_2_SQRTPI / M_SQRT2 / x * (1 - EXP(-SQR(x) / 2));
  @inline
  def computeP(w: Double, c: Double): Double = {
    val x = w / c
    val _1 = Erf.erfc( x / M_SQRT2)
    val _2 = M_2_SQRTPI / M_SQRT2 / x
    val _3 = 1 - StrictMath.exp( - x * x/ 2 )
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
    import StrictMath._
    val mu = 1 - pow(computeP(Defs.W_default, 1), k / 2)
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
        h = (h & Defs.TWO_TO_32_MINUS_1) + 5 * (h >>> 32)
        if (h >= Defs.UH_PRIME_DEFAULT)
          h -= Defs.UH_PRIME_DEFAULT
      }
      h.toInt
  }

  // determineRTCoefficients
  def estimate3Time(session: Session): (Double, Double, Double) = {
    val vectorCount = session.vectors.length
    var _sampleSize = vectorCount / 50
    _sampleSize = if (_sampleSize < 100) vectorCount
    else  if (_sampleSize > 10000) 10000 else _sampleSize

    val sample = new ArrayBuffer[ArrayVec[_]](_sampleSize)
    for (i <- session.random.shuffle((0 until session.vectors.length).toList).take(_sampleSize)) {
      sample += session.vectors(i)
    }

    val paramM = computeMfromK_P(Defs.K_default, session.successProb)
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

  implicit def applyConversion(r: Random): RandomExt = new RandomExt(r.self)

}
