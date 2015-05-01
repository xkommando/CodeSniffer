package codesniffer.core

import codesniffer.core.CharacVec.MethodCall



object CharacVec {
  type MethodCall = (String, String)
}
/**
 *
 * @tparam T dimension type
 *
 * Created by Bowen Cai on 4/10/2015.
 */
case class CharacVec[T](indexer: Indexer[T],
                    location: Location,
                    methodName: String,
                    descriptor: MethodDescriptor, // TODO: integrate with symbol table
                    calls: Option[Seq[MethodCall]]
                      ) {//extends IndexedSeq[Int] with IndexedSeqLike[Int, CharacVec[T]] {


  var id = -1
  private var vector: Array[Int] = new Array[Int](64)
  private[this] var _count = 0


  def count = _count
  def validNodeTypeCount = indexer.maxIndex + 1
//  override def seq = vector

  lazy val intern: Array[Int] = {
    val vecLen = vector.length
    val validCount = indexer.maxIndex + 1
    val sz = if (vecLen > validCount) validCount else vecLen
    val arr = new Array[Int](validCount)
    System.arraycopy(vector, 0, arr, 0, sz)
    vector = arr
    vector

  }

  def length = indexer.maxIndex + 1

  def apply(index: Int): Int = vector(index)

  def apply(name: T): Int = {
    val optIdx = indexer.probe(name)
    if (optIdx.isDefined && optIdx.get < vector.length)
      vector(optIdx.get)
    else throw new NoSuchElementException(name.toString)
  }

  def get(name: T): Option[Int] = {
    val optIdx = indexer.probe(name)
    if (optIdx.isDefined && optIdx.get < vector.length)
      Some(vector(optIdx.get))
    else None
  }
  

  /**
   *
   * @param name
   * @param weight of this type, by default 1
   * @return
   */
  def put(name: T, weight: Int  = 1): Int = {
    val idx = indexer.indexOf(name)
    ensureSize(idx + 1)
    vector(idx) += weight
    _count += weight
    _count
  }

  def remove(name: T, weight: Int  = 1): Int = {
    val optIdx = indexer.probe(name)
    if (optIdx.isDefined && optIdx.get < vector.length) {
      vector(optIdx.get) -= weight
      _count -= weight
      _count
    } else throw new NoSuchElementException(name.toString)
  }

  def update(name: T, weight: Int): Int = {
    val optIdx = indexer.probe(name)
    if (optIdx.isDefined && optIdx.get < vector.length) {
      vector(optIdx.get) -= weight
      _count = weight
      _count
    } else throw new NoSuchElementException(name.toString)
  }

  def clear(): Unit = {
    for (i <- 0 until vector.length)
      vector(i) = 0
    _count = 0
  }

  /**
   * merge one vector to this one, i.e., accumulate occurrence count
   * @param other
   */
  def merge(other: CharacVec[T]): this.type = {
    if (other.indexer == indexer) {
      val otherSz = other.vector.length
      val minLen = if (otherSz > vector.length) {
        ensureSize(otherSz)
        vector.length
      } else otherSz

      for (idx <- 0 to minLen) {
        vector(idx) += other.vector(idx)
      }
      _count += other.count

    } else throw new IllegalArgumentException(
      s"Could not merge vectors built under different indexer(different coordinates): $this, \r\n$other")
    this
  }

  def +=(other: CharacVec[T]): Unit = merge(other)

  protected def ensureSize(n: Int): Unit = {
    if (n > vector.length) {
      var newsize = vector.length * 2
      while (n > newsize)
        newsize = newsize * 2

      val newar: Array[Int] = new Array(newsize)
      System.arraycopy(vector, 0, newar, 0, vector.length)
      vector = newar
    }
  }

    /**
     * for debug
     * @return
     */
    override def toString = {
      val sb = new StringBuilder(1024, location.toString).append('\n')
      sb.append(methodName).append(':')
      descriptor.appendTo(sb).append('\t').append(_count).append(" nodes\n")
      vector.take(indexer.maxIndex + 1).addString(sb, " ")
      sb.toString()
    }

  object math {

    def EuclideanDist(other: CharacVec[T]): Double = {
      if (other.indexer == indexer)
         X.EuclideanDist(intern, other.intern, length)
      else throw new IllegalArgumentException(
        s"Could not calculate Euclidean distance of two vectors in different coordinates: $this, \r\n$other")
    }
  }
}
//object CharacVec {
//
//  def apply[T](indexer: Indexer[T],
//               location: Location,
//               methodName: String,
//               signature: MethodSignature) = new CharacVec[T](indexer, location, methodName, signature)
//
//
//  def newBuilder[T](indexer: Indexer[T],
//                    location: Location,
//                    methodName: String,
//                    signature: MethodSignature): Builder[Int, CharacVec[T]] =
//    new ArrayBuffer mapResult { x: ArrayBuffer[Int] => fromSeq(name, x) }
//
//  implicit def canBuildFrom[T]: CanBuildFrom[CharacVec[T], Int, CharacVec[T]] =
//    new CanBuildFrom[CharacVec[_], Int, CharacVec[Int]] {
//      def apply(): Builder[Int, CharacVec[T]] = newBuilder("default")
//      def apply(from: CharacVec[_]): Builder[Int, CharacVec[T]] =
//        newBuilder(from.name)
//    }
//}