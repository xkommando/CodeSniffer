package codesniffer.deckard

import scala.collection.mutable
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
  val funcCalls: mutable.Builder[FunctionCall, ArrayBuffer[FunctionCall]]

//  val defaultWeight = 1

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
 *
   * @param name
   * @return
   */
  def remove(name: T): Int

  def update(name: T, weight: Int): Int

  /**
   * merge one vector to this one, i.e., accumulate occurrence count
 *
   * @param other
   */
  def merge(other: CharacVec[T]): this.type

  def clear(): Unit
}

class NOPVec[T](val location: Location) extends CharacVec[T] {

  val funcCalls = new Dummy[FunctionCall]

  def count: Int = -1

  def put(name: T, weight: Int): Int = -1

  def clear(): Unit = {}

  def distance[A](other: CharacVec[A]): Double = -1.0
  def length: Int = -1
  def apply(index: Int): Int = -1
  def apply(name: T): Int = -1
  def get(name: T): Option[Int] = None
  def remove(name: T): Int = -1
  def update(name: T, weight: Int): Int = -1
  def merge(other: CharacVec[T]): this.type = this

}

