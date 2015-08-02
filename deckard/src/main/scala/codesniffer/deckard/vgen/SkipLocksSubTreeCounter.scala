package codesniffer.deckard.vgen

import codesniffer.api.expr.{MethodCallExpr, EnclosedExpr, Expression}
import codesniffer.api.stmt._

/**
 *
 *
 * Created by Bowen Cai on 5/21/2015.
 */
class SkipLocksSubTreeCounter[F] extends SubTreeCounter[F] {

  val locks = new ThreadLocal[Int]()
  locks.set(0)

  @inline
  override protected def exprCount(expr: Expression)(implicit ctx: Context[F]): Int = expr match {
    // skip enclosed expr
    case e: EnclosedExpr =>
      exprCount(e.getInner)
    case call: MethodCallExpr =>
      call.getName match {
        case "lock" => locks.set(locks.get() + 1)
          0
        case "unlock" => locks.set(locks.get() - 1)
          0
        case _ =>
          1
      }
    case ot =>  1 + kidsCount(expr.getChildrenNodes)
  }


  override protected def stmtCount(stmt: Statement)(implicit ctx: Context[F]): Int = stmt match {
    // skip ExpressionStmt
    case est: ExpressionStmt =>
      exprCount(est.getExpression)
    // skip BlockStmt
    case bst: BlockStmt =>
      kidsCount(bst.getStmts)
    case syncStmt: SynchronizedStmt =>
      val bk = syncStmt.getBlock
      if (bk != null)
        kidsCount(bk.getStmts)
      else 0

    case tryStmt: TryStmt =>
      var c = kidsCount(tryStmt.getResources)
      val bk = tryStmt.getTryBlock
      if (bk != null)
             c += kidsCount(bk.getStmts)
      val ct = tryStmt.getCatchs
      c += kidsCount(ct)

      val fb = tryStmt.getFinallyBlock
      if (fb != null) {
        val lks = locks.get()
        kidsCount(fb.getStmts)
        val lks2 = locks.get()
        if (lks2 >= lks && ct != null && ct.size > 0) // no unlock op, or got catch claus
          c + 1
        else
          c // try skpped

      } else c + 1
    // filter others stmt
    case fst =>
      1 + kidsCount(stmt.getChildrenNodes)
  }



}
