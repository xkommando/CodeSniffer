package codesniffer.core

import scala.collection.immutable.HashMap
import scala.collection.mutable.{ArrayBuffer, Builder}

/**
 * Created by Bowen Cai on 5/20/2015.
 */
class WeightedVec(self: CharacVec[String]) extends CharacVec[String] {

  override def put(name: String): Int = {
    val op = weightMap.get(name)
    val weight = if (op.isDefined) op.get
    else 1
    self.put(name, weight)
  }

  protected val weightMap = HashMap[String, Int](
    ("IfStmt",3),
    ("WhileStmt",3),
    ("ForeachStmt", 3),
    ("SwitchStmt",3),
    ("ForStmt",3),
    ("TryStmt",2),
    ("DoStmt",2),
    ("ContinueStmt",2),
    ("BreakStmt",2),
    ("ReturnStmt",2),

    ("ArrayAccessExpr",2),
    ("ObjectCreationExpr",2),
    ("ConditionalExpr",2),
    ("AssignExpr",2),
    ("InstanceOfExpr",2)
  )


  val funcCalls: Builder[FunctionCall, ArrayBuffer[FunctionCall]] = self.funcCalls

  def distance[A](other: CharacVec[A]): Double = self.distance(other)
  def location: Location = self.location

  def length = self.length
  def count = self.count

  def apply(index: Int): Int = self.apply(index)

  def apply(name: String): Int = self.apply(name)

def put(name: String, weight: Int): Int = self.put(name, weight)

  def get(name: String): Option[Int] = self.get(name)

  def remove(name: String): Int = self.remove(name)

  def update(name: String, weight: Int): Int = self.update(name, weight)

  def merge(other: ArrayVec[String]): this.type = {
    self.merge(other)
    this
  }
  def clear(): Unit = self.clear()
}
