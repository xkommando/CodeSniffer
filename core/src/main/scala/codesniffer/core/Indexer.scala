package codesniffer.core

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

  def maxIndex = val2IndexMap.size

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
