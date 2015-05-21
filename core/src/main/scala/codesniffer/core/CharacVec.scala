package codesniffer.core


import scala.collection.mutable.{ArrayBuffer, Builder}


/**
 *
 * @tparam T dimension type
 *
 * Created by Bowen Cai on 4/10/2015.
 */
trait CharacVec[T] {

  type FunctionCall = (String, String)

  var data: Option[AnyRef] = None
  val funcCalls: Builder[FunctionCall, ArrayBuffer[FunctionCall]]

  def distance[A](other: CharacVec[A]): Double
  def location: Location

  def length: Int
  def count: Int

  def apply(index: Int): Int

  def apply(name: T): Int

  /**
   *
   * @param name
   * @param weight of this type, by default 1
   * @return
   */
  def put(name: T, weight: Int): Int
  def put(name: T): Int = put(name, 1)

  def get(name: T): Option[Int]

  def +=(name: T, weight: Int): this.type = {
    put(name, weight)
    this
  }

  def +=(name: T): this.type = {
    put(name, 1)
    this
  }

  /**
   * redece to zero
   * @param name
   * @return
   */
  def remove(name: T): Int

  def update(name: T, weight: Int): Int

  /**
   * merge one vector to this one, i.e., accumulate occurrence count
   * @param other
   */
  def merge(other: ArrayVec[T]): this.type

  def clear(): Unit
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