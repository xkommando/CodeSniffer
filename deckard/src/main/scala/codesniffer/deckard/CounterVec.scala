package codesniffer.deckard

/**
 * Created by Bowen Cai on 8/2/2015.
 */
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
  def merge(other: CharacVec[T]): this.type = ???

}
