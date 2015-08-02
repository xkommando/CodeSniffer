package codesniffer.deckard

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * Created by Bowen Cai on 5/18/2015.
 */
class Dummy[T] extends mutable.Builder[T, ArrayBuffer[T]] {
  def +=(elem: T): this.type =this
  def clear(): Unit = {}
  def result(): ArrayBuffer[T] = ???
}
