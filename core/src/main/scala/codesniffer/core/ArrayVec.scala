package codesniffer.core

import scala.collection.mutable.ArrayBuffer


/**
 * Created by Bowen Cai on 5/18/2015.
 */
case class ArrayVec[T](indexer: Indexer[T],
                       location: Location,
                       methodName: String,
                       descriptor: MethodDescriptor // TODO: integrate with symbol table
                        ) extends CharacVec[T] {//extends IndexedSeq[Int] with IndexedSeqLike[Int, CharacVec[T]] {

  //  var id = -1

  private var vector: Array[Int] = new Array[Int](64)
  val funcCalls = new ArrayBuffer[FunctionCall]
  private[this] var _count = 0


  override def count = _count
  def validNodeTypeCount = indexer.maxIndex + 1
  //  override def seq = vector

  lazy val intern: Array[Int] = {
    val vecLen = vector.length
    val validCount = indexer.maxIndex + 1
    if (validNodeTypeCount != vecLen) {
      val sz = if (validCount > vecLen) vecLen else validCount
      val arr = new Array[Int](validCount)
      System.arraycopy(vector, 0, arr, 0, sz)
      vector = arr
    }
    vector
  }

  override def length = indexer.maxIndex + 1

  override def apply(index: Int): Int = vector(index)

  override def apply(name: T): Int = {
    val optIdx = indexer(name)
    if (optIdx.isDefined && optIdx.get < vector.length)
      vector(optIdx.get)
    else throw new NoSuchElementException(name.toString)
  }

  override def get(name: T): Option[Int] = {
    val optIdx = indexer(name)
    if (optIdx.isDefined && optIdx.get < vector.length)
      Some(vector(optIdx.get))
    else None
  }

  override def put(name: T, weight: Int): Int = {
    val idx = indexer.indexOf(name)
    ensureSize(idx + 1)
    vector(idx) += weight
    _count += 1
    _count
  }

  override def remove(name: T): Int = {
    val optIdx = indexer(name)
    if (optIdx.isDefined && optIdx.get < vector.length) {
      vector(optIdx.get) = 0
      _count -= 1
      _count
    } else throw new NoSuchElementException(name.toString)
  }

  override def update(name: T, weight: Int): Int = {
    val optIdx = indexer(name)
    if (optIdx.isDefined && optIdx.get < vector.length) {
      vector(optIdx.get) -= weight
      _count = weight
      _count
    } else throw new NoSuchElementException(name.toString)
  }

  override def clear(): Unit = {
    for (i <- 0 until vector.length)
      vector(i) = 0
    _count = 0
  }


  override def merge(other: CharacVec[T]): this.type = {
    other match {
      case a: ArrayVec[T] =>
        if (a.indexer == indexer) {
          val otherSz = a.vector.length
          val minLen = if (otherSz > vector.length) {
            ensureSize(otherSz)
            vector.length
          } else otherSz

          for (idx <- 0 to minLen) {
            vector(idx) += a.vector(idx)
          }
          _count += other.count

        } else throw new IllegalArgumentException(
          s"Could not merge vectors built under different indexer(different coordinates): $this, \r\n$other")
      case _ => ???
    }
    this
  }

  def +=(other: ArrayVec[T]): Unit = merge(other)

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
//    vector.take(indexer.maxIndex + 1).addString(sb, " ")
    sb.toString()
  }

  override def distance[A](other: CharacVec[A]): Double = other match {
    case other: ArrayVec[_] => descriptor.distance(other.descriptor)
      + math.EuclideanDist(other)
    case _=> ???
  }

  object math {

    def EuclideanDist[A](other: ArrayVec[A]): Double = {
      if (other.indexer == indexer) {
        val sl = vector.length
        val ol = other.vector.length
        if (sl < ol) {
          ensureSize(ol)
          MathUtils.EuclideanDist(vector, other.vector, ol)
        } else if (ol < sl) {
          other.ensureSize(sl)
          MathUtils.EuclideanDist(vector, other.vector, sl)
        } else MathUtils.EuclideanDist(vector, other.vector, sl)
      } else throw new IllegalArgumentException(
        s"Could not calculate Euclidean distance of two vectors in different coordinates: \r\n$this, \r\n$other")
    }

    def CosineDist[A](other: ArrayVec[A]): Double = {
      if (other.indexer == indexer) {
        val sl = vector.length
        val ol = other.vector.length
        if (sl < ol) {
          ensureSize(ol)
          MathUtils.CosineDist(vector, other.vector, ol)
        } else if (ol < sl) {
          other.ensureSize(sl)
          MathUtils.CosineDist(vector, other.vector, sl)
        } else MathUtils.CosineDist(vector, other.vector, sl)
      } else throw new IllegalArgumentException(
        s"Could not calculate Cosine distance of two vectors in different coordinates: \r\n$this, \r\n$other")
    }

  }
}