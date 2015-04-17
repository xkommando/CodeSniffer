package codesniffer.core


import scala.collection.mutable
import com.github.javaparser.ast.Node

/**
 * Created by Bowen Cai on 4/16/2015.
 */
class Context(val config: Config, val location: Location) {

  private val type2IndexMap = new mutable.HashMap[Class[_], Int]
  type2IndexMap.sizeHint(256)

  private var index2TypeMap = new Array[Class[_]](128)

  def index[T <: Node](klass: Class[T]): Int = {
    type2IndexMap.getOrElse(klass, {
      val index = type2IndexMap.size
      type2IndexMap.put(klass, index)

      val mapSz = index2TypeMap.length
      if (index >= mapSz) {
        val newar: Array[Class[_]] = new Array(mapSz * 2)
        System.arraycopy(index2TypeMap, 0, newar, 0, mapSz)
        index2TypeMap = newar
      }
      index2TypeMap(index) = klass
      index
    })
  }

  def klass(idx: Int): Option[Class[_]] =
    if (idx < index2TypeMap.length)
      Option(index2TypeMap(idx)) // may be null
    else None


}
