package codesniffer.core

import com.github.javaparser.ast.Node

import scala.collection.mutable.ArrayBuffer

/**
 * Created by Bowen Cai on 4/16/2015.
 */
//object CharacVec {
//  val empty = new CharacVec(null)
//}
case class CharacVec(context: Context,
                    location: Location,
                    methodName: String,
                    signature: MethodSignature // TODO: replaced with symbol table later
                      ) {

  private var vector: Array[Int] = new Array[Int](64)
  private[this] var _count = 0

  def count = _count

  /**
   * put one node to this vector, the occurrence count of this type of node will ++
   *
   * @param o
   * @tparam T
   * @return
   */
  def put[T <: Node](o: T): Int = {
    val idx = context.index(o.getClass)
    ensureSize(idx + 1)
    vector(idx) += 1
    _count += 1
    _count
  }

  /**
   * merge one vector to this one, i.e., accumulate occurrence count
   * @param other
   */
  def merge(other: CharacVec): Unit = {
    if (other.context == context) {
      val otherSz = other.vector.size
      val minLen = if (otherSz > vector.length) {
        ensureSize(otherSz)
        vector.length
      } else otherSz

      for (idx <- 0 to minLen) {
        vector(idx) += other.vector(idx)
      }
      _count += other.count

    } else throw new IllegalArgumentException(
      s"Could not merge vectors built under different contexts vectors: $this, \r\n$other")

  }

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
      val sb = new StringBuilder(2048)
      for (i <- 0 until vector.length) {
        val tp = context.klass(i)
        val name = if (tp.isDefined)
          tp.get.getSimpleName
        else "UNK"
        sb.append(name + ":" + vector(i)).append("\r\n")
      }
      sb.toString()
    }
}
