package codesniffer.deckard.vgen

import java.lang.reflect.Modifier

import codesniffer.api.Node
import codesniffer.api.`type`._
import codesniffer.api.body.{ClassOrInterfaceDeclaration, MethodDeclaration}
import codesniffer.api.expr._
import codesniffer.api.stmt.{BlockStmt, ExpressionStmt, Statement}
import codesniffer.api.visitor.{VoidVisitor, VoidVisitorAdapter}
import codesniffer.core._
import codesniffer.deckard.{MethodDescriptor, ArrayVec, CharacVec}

import scala.beans.BeanProperty
import scala.collection.convert.wrapAsScala._

/**
 *  generate vector in a top-down approche
 *
 * Created by Bowen Cai on 4/10/2015.
 */
object BasicVecGen {

  @inline
  def newVec[F](m: MethodDeclaration, ctx: Context[F]): CharacVec[F] =
  new ArrayVec[F](ctx.indexer, ctx.currentLocation, m.getName, extractSignature(m)).asInstanceOf[CharacVec[F]]

  @inline
  def writeVec[F](m: MethodDeclaration, v: CharacVec[F], ctx: Context[F]): Unit = ctx.vecWriter.write(v)

  @inline
  def extractSignature(m: MethodDeclaration): MethodDescriptor = {

    val paramTypeNames = if (null == m.getParameters || m.getParameters.size() == 0) None
    else Some(m.getParameters.map{p=>extractTypeName(p.getType)}.toSet)
    val annotationNames = if (null == m.getAnnotations || m.getAnnotations.size() == 0) None
    else Some(m.getAnnotations.map(_.getName.getName).toSet)
    val throwNames = if (null == m.getThrows || m.getThrows.size() == 0) None
    else Some(m.getThrows.map(_.getName).toSet)

    new MethodDescriptor(m.getModifiers,
      extractTypeName(m.getType),
      paramTypeNames,
      annotationNames,
      throwNames)
  }

  @inline
  def extractTypeName(tp: Type): String = tp match {
    case a : ClassOrInterfaceType =>
      a.getName
    case a : PrimitiveType => a.getType.name()
    case a : ReferenceType =>
      val name = extractTypeName(a.getType)
      val dim = a.getArrayCount
      if (dim == 0) name
      else dim + "D-Array[" + name + "]"
    case _ : VoidType => "void"
    case _ : WildcardType => "Wildcard"
    case _  => "UNK"
  }

  protected def calleeName(exp: Expression): String = exp match {
    case fa: FieldAccessExpr => fa.getScope + "." + fa.getField
    case nm: NameExpr => nm.getName
    case t: ThisExpr => "this"
    case null => "this"
    case s: SuperExpr => "super"
    case mc: MethodCallExpr => calleeName(mc.getScope) + "." + mc.getName // actually we need the return type
    case pt: EnclosedExpr => calleeName(pt.getInner)
    case klass: ClassExpr => extractTypeName(klass.getType) + ".class"
    case cst: CastExpr => extractTypeName(cst.getType)
    case str: StringLiteralExpr => "String"
    case arr: ArrayAccessExpr => calleeName(arr.getName)
    case nobj: ObjectCreationExpr => extractTypeName(nobj.getType)
    case cnd: ConditionalExpr => calleeName(cnd.getThenExpr)
    case as: AssignExpr => calleeName(as.getTarget)
    case bin: BinaryExpr => calleeName(bin.getLeft)
    case a =>
      println(s"========================================\r\n"
        + s"${a.getParentNode}\r\n$a\r\n${a.getParentNode.getParentNode}\r\n${a.getClass}"
        + s"\r\n===============================")
      "UNK"
  }
}

class BasicVecGen[F] extends VoidVisitorAdapter[Context[F]] {

  @BeanProperty var classVisitor: VoidVisitor[Context[F]] = _

  var before: (MethodDeclaration, Context[F])=> CharacVec[F] = BasicVecGen.newVec[F]
  var after: (MethodDeclaration, CharacVec[F], Context[F])=> Unit = BasicVecGen.writeVec[F]

  override def visit(method: MethodDeclaration, ctx: Context[F]): Unit =
    if (!ctx.config.filterMethod(method)) {
      val modifiers = method.getModifiers
      if (!Modifier.isAbstract(modifiers) && !Modifier.isNative(modifiers)
        && method.getBody != null
        && method.getBody.getStmts != null && method.getBody.getStmts.size() > 0) {

        val methodName = method.getName

        val prevLoc = ctx.currentLocation
        ctx.currentLocation = ctx.currentLocation.enterMethod(methodName, method.getBeginLine, method.getEndLine)
        val vec = before(method, ctx)

        try {
          collectNodes(method.getBody.getStmts, vec)(ctx)
        } catch {
          case e: Exception => throw new RuntimeException(s"Could not travel though method ${ctx.currentLocation}", e)
        }
        after(method, vec, ctx)
        ctx.currentLocation = prevLoc
      }
    }

  @inline
  def collectNodes[E <:Node](nodes: java.util.List[E], vec: CharacVec[F])(implicit ctx: Context[F]): Unit = {
    if (nodes != null && nodes.size > 0)
      for (n <- nodes)
        collectNode(n, vec)(ctx)
  }

  def collectNode(pnode: Node, vec: CharacVec[F])(implicit ctx: Context[F]): Unit = {
    val cfg = ctx.config
    if (!cfg.filterNode(pnode)) {
      if (ctx.config.skipNode(pnode)) {
        collectNodes(pnode.getChildrenNodes, vec)
      } else
        pnode match {
          case stmt: Statement => collectStmt(stmt, vec)
          case expr: Expression =>  collectExpr(expr, vec)
          // continue with new class
          case tp: ClassOrInterfaceDeclaration if !tp.isInterface =>
            classVisitor.visit(tp, ctx)
          case node => putNode(node, vec)
        }
    }
  }

  @inline
  protected def collectExpr(expr: Expression, vec: CharacVec[F])(implicit ctx: Context[F]): Unit = {
    if (!ctx.config.filterExpr(expr)) {
      if (ctx.config.skipExpr(expr))
        collectNodes(expr.getChildrenNodes, vec)
      else
        expr match {
          // skip enclosed expr
          case e: EnclosedExpr =>
            collectExpr(e.getInner, vec)
          case ot => putNode(ot, vec)
        }
    }
  }


  @inline
  protected def collectStmt(stmt: Statement, vec: CharacVec[F])(implicit ctx: Context[F]): Unit = {
    if (!ctx.config.filterStmt(stmt)) {
      if (ctx.config.skipStmt(stmt))
        collectNodes(stmt.getChildrenNodes, vec)
      else
        stmt match {
          // skip ExpressionStmt
          case est: ExpressionStmt =>
            collectNode(est.getExpression, vec)
          // skip BlockStmt
          case bst: BlockStmt =>
            collectNodes(bst.getStmts, vec)
          // filter others stmt
          case fst =>
            if (!ctx.config.filterStmt(fst))
              putNode(fst, vec)
        }
    }
  }

  @inline
  protected def putNode(node: Node, vec: CharacVec[F])(implicit ctx: Context[F]): Unit =
    node match {
      case call: MethodCallExpr =>
          addMethodCall(call, vec)
      case _ =>
        vec.put(findNodeName(node).asInstanceOf[F])
        collectNodes(node.getChildrenNodes, vec)
    }

  @inline
  protected[codesniffer] def addMethodCall(call: MethodCallExpr, vec: CharacVec[F])(implicit ctx: Context[F]): Unit = {
    vec.put("MethodCallExpr".asInstanceOf[F])
    val scope = call.getScope
    if (scope != null) {
      collectExpr(scope, vec)
//      val calleeName = BasicVecGen.calleeName(call.getScope)
//      val methodName = call.getName
//      val lsExps = call.getArgs
//      vec.funcCalls += (calleeName -> methodName)
    }
  }

  @inline
  protected[codesniffer] def findNodeName(node: Node): String = node match {
    case a: BooleanLiteralExpr =>
      if (a.getValue) "__BOOL_TRUE___"
      else "__BOOL_FALSE___"
    case a: PrimitiveType => a.getType.name()
    case a: BinaryExpr => a.getOperator.name()
    case a: UnaryExpr => a.getOperator.name()
    case a: ReferenceType =>
      val arr = a.getArrayCount
      if (arr == 0) "ReferenceType"
      else "ArrayType"
    case _ => node.getClass.getSimpleName
  }


}
