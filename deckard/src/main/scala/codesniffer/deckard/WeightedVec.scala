package codesniffer.deckard

import scala.collection.immutable.HashMap
import scala.collection.mutable.{ArrayBuffer, Builder}

/**
 * a wrapper instead of a sub-type of any specific CharacVec
 *
 * Created by Bowen Cai on 5/20/2015.
 */
class WeightedVec[F](val underlying: CharacVec[F]) extends CharacVec[F] {

  val defaultWeight = 2

  override def put(name: F): Int = {
    val op = weightMap.get(name.asInstanceOf[String])
    val weight = if (op.isDefined) op.get
    else defaultWeight
    underlying.put(name, weight)
  }

  protected val weightMap = HashMap[String, Int](
    ("IfStmt",4),
    ("WhileStmt",4),
    ("ForeachStmt", 4),
    ("SwitchStmt",4),
    ("ForStmt",4),
    ("TryStmt",3),
    ("DoStmt",3),
//    ("ContinueStmt",2),
//    ("BreakStmt",2),
//    ("ReturnStmt",2),

    ("ArrayAccessExpr", 3),
    ("ObjectCreationExpr", 3),
    ("ConditionalExpr", 3),
    ("AssignExpr", 3),
    ("InstanceOfExpr", 3),

    ("FieldAccessExpr", 1),

    ("StringLiteralExpr", 1),
    ("CharLiteralExpr", 1),
    ("IntegerLiteralExpr", 1),
    ("DoubleLiteralExpr", 1),
    ("LongLiteralExpr", 1),
    ("__BOOL_TRUE___", 1),
    ("__BOOL_FALSE___", 1),

    ("NameExpr", 1)
  )

  val funcCalls: Builder[FunctionCall, ArrayBuffer[FunctionCall]] = underlying.funcCalls

  def distance[A](other: CharacVec[A]): Double = other match {
    case w: WeightedVec[F] => underlying.distance(w.underlying)
    case a: ArrayVec[F] => underlying.distance(a)
    case _ => ???
  }

  def location: Location = underlying.location

  def length = underlying.length
  def count = underlying.count

  def apply(index: Int): Int = underlying.apply(index)

  def apply(name: F): Int = underlying.apply(name)

  def put(name: F, weight: Int): Int = underlying.put(name, weight)

  def get(name: F): Option[Int] = underlying.get(name)

  def remove(name: F): Int = underlying.remove(name)

  def update(name: F, weight: Int): Int = underlying.update(name, weight)

  def merge(other: CharacVec[F]): this.type = {
    other match {
      case a: ArrayVec[F] => underlying.merge(a)
      case w: WeightedVec[F] => underlying.merge(w.underlying)
      case _=> ???
    }
    this
  }
  def clear(): Unit = underlying.clear()
}
