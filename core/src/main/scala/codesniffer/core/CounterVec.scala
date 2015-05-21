package codesniffer.core


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

case class CounterVec[T](location: Location) extends CharacVec[T] {

  val funcCalls = new Dummy[FunctionCall]

  var _count = 0
  def count: Int = _count

  def put(name: T, weight: Int): Int = {
    _count += weight
    _count
  }

  def clear(): Unit = _count = 0

  def distance[A](other: CharacVec[A]): Double = ???
  def length: Int = ???
  def apply(index: Int): Int = ???
  def apply(name: T): Int = ???
  def get(name: T): Option[Int] = ???
  def remove(name: T): Int = ???
  def update(name: T, weight: Int): Int = ???
  def merge(other: ArrayVec[T]): this.type = ???

}
