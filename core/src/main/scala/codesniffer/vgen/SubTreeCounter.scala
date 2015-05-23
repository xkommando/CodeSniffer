package codesniffer.vgen

import codesniffer.core.CharacVec
import com.github.javaparser.ast.Node
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.expr.{EnclosedExpr, Expression}
import com.github.javaparser.ast.stmt.{SynchronizedStmt, BlockStmt, ExpressionStmt, Statement}

import scala.collection.convert.wrapAsScala._
/**
 * Created by Bowen Cai on 5/21/2015.
 */
class SubTreeCounter[F] extends BasicVecGen[F] {

  @inline
  override def collectNode(pnode: Node, vec: CharacVec[F])(implicit ctx: Context[F]): Unit
  = nodeCount(pnode)

  @inline
  def kidsCount[E <:Node](kids: java.util.List[E])(implicit ctx: Context[F]): Int = {
    if (kids != null && kids.size() > 0) {
      kids.foreach(nodeCount)
      kids.foldLeft(0) { (sum, node) =>
        sum + (if (node != null)
          node.getData.asInstanceOf[Int]
        else 0)
      }
    } else 0
  }

  def nodeCount(pnode: Node)(implicit ctx: Context[F]): Int = {
    val cfg = ctx.config
    if (!cfg.filterNode(pnode)) {
      val curLevelCount = pnode match {
        case stmt: Statement =>
          if (!cfg.filterStmt(stmt)) {
            if (ctx.config.skipStmt(stmt))
              kidsCount(stmt.getChildrenNodes)
            else stmtCount(stmt)

          } else 0
        // filter expr
        case expr: Expression =>
          if (!cfg.filterExpr(expr)) {
            if (cfg.skipExpr(expr))
              kidsCount(expr.getChildrenNodes)
            else exprCount(expr)

          } else 0

        // continue with new class
        case tp: ClassOrInterfaceDeclaration if !tp.isInterface =>
          classVisitor.visit(tp, ctx)/////////////////
          0
        case node => 1 + kidsCount(node.getChildrenNodes)
      }

      pnode.setData(curLevelCount)
      curLevelCount
    } else {
      pnode.setData(0)
      0
    }
  }

  @inline
  protected def exprCount(expr: Expression)(implicit ctx: Context[F]): Int = expr match {
    // skip enclosed expr
    case e: EnclosedExpr =>
      exprCount(e.getInner)
    case ot =>  1 + kidsCount(expr.getChildrenNodes)
  }

  @inline
  protected def stmtCount(stmt: Statement)(implicit ctx: Context[F]): Int = stmt match {
    // skip ExpressionStmt
    case est: ExpressionStmt =>
      exprCount(est.getExpression)
    // skip BlockStmt
    case bst: BlockStmt =>
      kidsCount(bst.getStmts)
    // filter others stmt
    case fst =>
      1 + kidsCount(stmt.getChildrenNodes)
  }




}
