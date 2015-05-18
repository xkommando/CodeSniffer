package codesniffer.core

import scala.collection.mutable
import scala.reflect.ClassTag

/**
 *  provide index for a certain kind of value
 *
 * Created by Bowen Cai on 4/13/2015.
 */
class Indexer[T: ClassTag] {

  private val val2Index = new mutable.HashMap[T, Int]
  val2Index.sizeHint(256)

  private var index2Val = new Array[T](128)

  def maxIndex = val2Index.size - 1

  def probe(value: T): Option[Int] = val2Index.get(value)

  def apply(value: T): Option[Int] = val2Index.get(value)
  def apply(idx: Int): Option[T] = valueAt(idx)

  /**
   *
   * @param value
   * @return index for this value, create new index if this value is new
   */
  def indexOf(value: T): Int = val2Index.getOrElse(value, {
    val index = val2Index.size
    val2Index.put(value, index)

    val mapSz = index2Val.length
    if (index >= mapSz) {
      val newar = new Array[T](mapSz * 2)
      System.arraycopy(index2Val, 0, newar, 0, mapSz)
      index2Val = newar
    }
    index2Val(index) = value
    index
  })

  def valueAt(idx: Int): Option[T] =
    if (idx < index2Val.length)
      Option(index2Val(idx)) // may be null
    else None

  /**
   * remove entry of of this value
   * @param value
   * @return index of this key, None if not find
   */
  def remove(value: T): Option[Int] = {
    val optIdx = val2Index.remove(value)
    if(optIdx.isDefined) {
      index2Val(optIdx.get) = null.asInstanceOf[T]
    }
    optIdx
  }

  /**
   * remove entry at this index
   * @param index
   * @return key, i.e., the value at this index, None if not find
   */
  def remove(index: Int): Option[T] = {
    val optP = val2Index.find(_._2 == index)
    if (optP.isDefined){
      val pair = optP.get
      val2Index -= pair._1
      index2Val(pair._2) = null.asInstanceOf[T]
      Some(optP.get._1)
    } else None
  }

  /**
   * clear this indexer, but keep the memory
   */
  def clear(): Unit = {
    for(i <- 0 until index2Val.length)
      index2Val(i) = null.asInstanceOf[T]
    val2Index.clear()
  }

  def appendTo(sb: mutable.StringBuilder): mutable.StringBuilder = index2Val.take(val2Index.size).addString(sb, " ")
  override def toString = appendTo(new StringBuilder(val2Index.size * 15)).toString()


  override def hashCode(): Int = {
    var result = 1
    for (i <- 0 to maxIndex) {
      val e = index2Val(i)
      result = 31 * result + (if (e == null) 0 else e.hashCode)
    }
    result
  }

  override def equals(o: Any): Boolean = o match {
    case other: Indexer[T] =>
      if (this eq other) true
      else
        if (other == null || maxIndex != other.maxIndex)
          false
        else {
        for (i <- 0 to maxIndex) {
          val o1 = index2Val(i)
          val o2 = other.index2Val(i)
          if (!(if (o1 == null) o2 == null else o1 == o2)) return false
        }
        true
      }
    case _ => false
  }

}
