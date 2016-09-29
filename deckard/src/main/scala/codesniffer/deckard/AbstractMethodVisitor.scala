package codesniffer.deckard

import java.lang.reflect.Modifier

import codesniffer.api.Node
import codesniffer.api.body.{ClassOrInterfaceDeclaration, MethodDeclaration}
import codesniffer.api.expr._
import codesniffer.api.stmt.{IfStmt, SwitchStmt, _}
import codesniffer.api.visitor.{VoidVisitor, VoidVisitorAdapter}
import codesniffer.deckard.vgen.{Context, FinallyBlockStmt}

import scala.beans.BeanProperty
import scala.collection.convert.wrapAsScala._

/**
  * Created by superbow on 9/17/2016.
  */
abstract class AbstractMethodVisitor[T] extends VoidVisitorAdapter[Context[T]] {

  @BeanProperty var classVisitor: VoidVisitor[Context[T]] = _

  var before: (MethodDeclaration, Context[T]) => Unit = _
  var after: (MethodDeclaration, Context[T]) => Unit = _

  override def visit(method: MethodDeclaration, ctx: Context[T]): Unit =
    if (!ctx.config.filterMethod(method)) {
      val modifiers = method.getModifiers
      if (!Modifier.isAbstract(modifiers) && !Modifier.isNative(modifiers)
        && method.getBody != null
        && method.getBody.getStmts != null && method.getBody.getStmts.size() > 0) {

        val methodName = method.getName

        val prevLoc = ctx.currentLocation
        ctx.currentLocation = ctx.currentLocation.enterMethod(methodName, method.getBeginLine, method.getEndLine)
        if (before != null)
          before(method, ctx)

        try {
          collectNodes(method.getBody.getStmts, ctx)
        } catch {
          case e: Exception => throw new RuntimeException(s"Could not travel though method ${ctx.currentLocation}", e)
        }
        if (after != null)
          after(method, ctx)
        ctx.currentLocation = prevLoc
      }
    }

  def putNode(node: Node, ctx: Context[T]): Unit

  @inline
  def collectNodes[E <: Node](nodes: java.util.List[E], ctx: Context[T]): Unit = {
    if (nodes != null && nodes.size > 0)
      for (n <- nodes)
        collectNode(n, ctx)
  }

  def collectNode(pnode: Node, ctx: Context[T]): Unit = {
    val cfg = ctx.config
    if (!cfg.filterNode(pnode)) {
      if (ctx.config.skipNode(pnode)) {
        collectNodes(pnode.getChildrenNodes, ctx)
      } else
        pnode match {
          case stmt: Statement => collectStmt(stmt, ctx)
          case expr: Expression => collectExpr(expr, ctx)
          // continue with new class
          case tp: ClassOrInterfaceDeclaration if !tp.isInterface =>
            classVisitor.visit(tp, ctx)
          case node => putNode(node, ctx)
        }
    }
  }

  @inline
  protected def collectExpr(expr: Expression, ctx: Context[T]): Unit = {
    if (!ctx.config.filterExpr(expr)) {
      if (ctx.config.skipExpr(expr)) {
        collectNodes(expr.getChildrenNodes, ctx)
      }
      else
        expr match {
          // skip enclosed expr
          case e: EnclosedExpr =>
            collectExpr(e.getInner, ctx)
          case ot => putNode(ot, ctx)
        }
    }
  }


  @inline
  protected def collectStmt(stmt: Statement, ctx: Context[T]): Unit = {
    if (!ctx.config.filterStmt(stmt)) {

      if (ctx.config.skipStmt(stmt))
        collectNodes(stmt.getChildrenNodes, ctx)
      else {
        stmt match {
          // skip ExpressionStmt
          case est: ExpressionStmt =>
            return collectExpr(est.getExpression, ctx)
          // skip BlockStmt
          case bst: BlockStmt =>
            return collectNodes(bst.getStmts, ctx)
          case _ =>
        }

        putNode(stmt, ctx)

        matchInner(stmt, ctx)

      } // no skip
    } // no filter
  }


  protected def matchInner(stmt: Statement, ctx: Context[T]) = stmt match {
    case doSt: DoStmt =>
      collectExpr(doSt.getCondition, ctx)
      val stmt = doSt.getBody
      if (stmt != null)
        collectStmt(stmt, ctx)


    case wst: WhileStmt =>
      collectExpr(wst.getCondition, ctx)
      val stmt = wst.getBody
      if (stmt != null)
        collectStmt(stmt, ctx)

    case fst: ForeachStmt =>
      collectExpr(fst.getIterable, ctx)
      collectExpr(fst.getVariable, ctx)
      val stmt = fst.getBody
      if (stmt != null)
        collectStmt(stmt, ctx)

    case ast: AssertStmt =>
      collectExpr(ast.getCheck, ctx)

    case sst: SynchronizedStmt =>
      collectExpr(sst.getExpr, ctx)
      collectNodes(sst.getBlock.getStmts, ctx)

    case forSt: ForStmt =>
      collectNodes(forSt.getInit, ctx)
      val cmpExp = forSt.getCompare
      if (cmpExp != null)
        collectExpr(cmpExp, ctx)
      collectNodes(forSt.getUpdate, ctx)
      val bdst = forSt.getBody
      if (bdst != null)
        collectStmt(bdst, ctx)

    case trySt: TryStmt =>
      collectNodes(trySt.getResources, ctx)
      collectNodes(trySt.getTryBlock.getStmts, ctx)
      val cas = trySt.getCatchs
      if (cas != null && cas.size() > 0) {
        for (ca <- cas) {
          putNode(ca, ctx)
          collectNode(ca.getCatchBlock, ctx)
        }
      }
      val fstmt = trySt.getFinallyBlock
      if (fstmt != null) {
        putNode(FinallyBlockStmt(fstmt), ctx)
        collectNodes(fstmt.getStmts, ctx)
      }

    case swst: SwitchStmt =>
      collectExpr(swst.getSelector, ctx)
      val cases = swst.getEntries
      if (cases != null && cases.length > 0) {
        for (cs <- cases)
          collectNodes(cs.getStmts, ctx)
      }

    case ifSt: IfStmt =>
      collectExpr(ifSt.getCondition, ctx)
      var stmt = ifSt.getThenStmt
      if (stmt != null)
        collectNode(stmt, ctx)
      stmt = ifSt.getElseStmt
      if (stmt != null)
        collectNode(stmt, ctx)
    case _ =>
  } // match

}
