package codesniffer.core

import scala.StringBuilder
import scala.collection.mutable
import scala.reflect.ClassTag

/**
 *  provide index for a certain kind of value
 *
 * Created by Bowen Cai on 4/13/2015.
 */
class Indexer[T: ClassTag] {

  private val val2IndexMap = new mutable.HashMap[T, Int]
  val2IndexMap.sizeHint(256)

  private var index2ValMap = new Array[T](128)

  def maxIndex = val2IndexMap.size - 1

  def probe(value: T): Option[Int] = val2IndexMap.get(value)

  /**
   *
   * @param value
   * @return index for this value, create new index if this value is new
   */
  def indexOf(value: T): Int = val2IndexMap.getOrElse(value, {
    val index = val2IndexMap.size
    val2IndexMap.put(value, index)

    val mapSz = index2ValMap.length
    if (index >= mapSz) {
      val newar = new Array[T](mapSz * 2)
      System.arraycopy(index2ValMap, 0, newar, 0, mapSz)
      index2ValMap = newar
    }
    index2ValMap(index) = value
    index
  })

  def valueAt(idx: Int): Option[T] =
    if (idx < index2ValMap.length)
      Option(index2ValMap(idx)) // may be null
    else None

  /**
   * remove entry of of this value
   * @param value
   * @return index of this key, None if not find
   */
  def remove(value: T): Option[Int] = {
    val optIdx = val2IndexMap.remove(value)
    if(optIdx.isDefined) {
      index2ValMap(optIdx.get) = null.asInstanceOf[T]
    }
    optIdx
  }

  /**
   * remove entry at this index
   * @param index
   * @return key, i.e., the value at this index, None if not find
   */
  def remove(index: Int): Option[T] = {
    val optP = val2IndexMap.find(_._2 == index)
    if (optP.isDefined){
      val pair = optP.get
      val2IndexMap -= pair._1
      index2ValMap(pair._2) = null.asInstanceOf[T]
      Some(optP.get._1)
    } else None
  }

  /**
   * clear this indexer, but keep the memory
   */
  def clear(): Unit = {
    for(i <- 0 until index2ValMap.length)
      index2ValMap(i) = null.asInstanceOf[T]
    val2IndexMap.clear()
  }

  def appendTo(sb: mutable.StringBuilder): mutable.StringBuilder = index2ValMap.take(val2IndexMap.size).addString(sb, " ")
  override def toString = appendTo(new StringBuilder(val2IndexMap.size * 15)).toString()


  override def equals(o: Any): Boolean = o match {
    case other: Indexer[T] =>
      if (this eq other) true
      else
        if (other == null || maxIndex != other.maxIndex)
          false
        else {
        for (i <- 0 to maxIndex) {
          val o1 = index2ValMap(i)
          val o2 = other.index2ValMap(i)
          if (!(if (o1 == null) o2 == null else o1 == o2)) return false
        }
        true
      }
    case _ => false
  }

}
