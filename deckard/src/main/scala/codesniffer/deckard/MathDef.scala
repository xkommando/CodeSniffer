package codesniffer.deckard

/**
 * Created by Bowen Cai on 4/25/2015.
 */
object MathDef {

  def ln = StrictMath.log _

  val log2baseE = StrictMath.log(2.0)
  val log3baseE = StrictMath.log(3.0)
  val log5baseE = StrictMath.log(5.0)
  val log6baseE = StrictMath.log(6.0)
  val log7baseE = StrictMath.log(7.0)
  val log9baseE = StrictMath.log(9.0)
  def log2 = StrictMath.log(_: Double) / log2baseE
  def log3 = StrictMath.log(_: Double) / log3baseE
  def log5 = StrictMath.log(_: Double) / log5baseE
  def log6 = StrictMath.log(_: Double) / log6baseE
  def log7 = StrictMath.log(_: Double) / log7baseE
  def log9 = StrictMath.log(_: Double) / log9baseE

//  log2(e)
  val M_LOG2E = 1.44269504088896340736

//  log10(e)
  val M_LOG10E = 0.434294481903251827651

//  ln(2)
  val M_LN2 = 0.693147180559945309417

//  ln(10)
  val M_LN10 = 2.30258509299404568402


  val M_E = StrictMath.E
  val M_PI = StrictMath.PI


//  sqrt(2)
  val M_SQRT2 = 1.41421356237309504880

//  1/sqrt(2)
  val M_SQRT1_2 = 0.707106781186547524401


//  pi/2
  val M_PI_2 = 1.57079632679489661923

//  pi/4
  val M_PI_4 = 0.785398163397448309616


  val M_1_PI = 0.318309886183790671538
//  2/pi
  val M_2_PI = 0.636619772367581343076

//  2/sqrt(pi)
  val M_2_SQRTPI = 1.12837916709551257390


}
