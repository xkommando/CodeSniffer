package codesniffer.vgen

import java.lang.reflect.Modifier

import codesniffer.core._
import codesniffer.vgen.Context
import com.github.javaparser.ast.Node
import com.github.javaparser.ast.`type`._
import com.github.javaparser.ast.body.{MethodDeclaration, ClassOrInterfaceDeclaration}
import com.github.javaparser.ast.expr._
import com.github.javaparser.ast.stmt.{BlockStmt, ExpressionStmt, Statement}
import com.github.javaparser.ast.visitor.{VoidVisitor, VoidVisitorAdapter}

import scala.beans.BeanProperty
import scala.collection.convert.wrapAsScala._

/**
 * Created by Bowen Cai on 4/10/2015.
 */
object BasicVGen {

  def newVec[F](m: MethodDeclaration, ctx: Context[F]): CharacVec[F] =
  new ArrayVec[F](ctx.indexer, ctx.currentLocation, m.getName, extractSignature(m)).asInstanceOf[CharacVec[F]]

  def passVec[F](m: MethodDeclaration, v: CharacVec[F], ctx: Context[F]): CharacVec[F] = v

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


  protected def findCallee(exp: Expression): String = exp match {
    case fa: FieldAccessExpr => fa.getScope + "." + fa.getField
    case nm: NameExpr => nm.getName
    case t: ThisExpr => "this"
    case null => "this"
    case s: SuperExpr => "super"
    case mc: MethodCallExpr => findCallee(mc.getScope) + "." + mc.getName // actually we need the return type
    case pt: EnclosedExpr => findCallee(pt.getInner)
    case klass: ClassExpr => extractTypeName(klass.getType) + ".class"
    case cst: CastExpr => extractTypeName(cst.getType)
    case str: StringLiteralExpr => "String"
    case arr: ArrayAccessExpr => findCallee(arr.getName)
    case nobj: ObjectCreationExpr => extractTypeName(nobj.getType)
    case cnd: ConditionalExpr => findCallee(cnd.getThenExpr)
    case as: AssignExpr => findCallee(as.getTarget)
    case bin: BinaryExpr => findCallee(bin.getLeft)
    case a =>
      println(s"========================================\r\n"
        + s"${a.getParentNode}\r\n$a\r\n${a.getParentNode.getParentNode}\r\n${a.getClass}"
        + s"\r\n===============================")
      "UNK"
  }
}

class BasicVGen[F] extends VoidVisitorAdapter[Context[F]] {

  @BeanProperty var classVisitor: VoidVisitor[Context[F]] = _

  var before: (MethodDeclaration, Context[F])=> CharacVec[F] = BasicVGen.newVec[F]
  var after: (MethodDeclaration, CharacVec[F], Context[F])=> CharacVec[F]  = BasicVGen.passVec[F]

  override def visit(method: MethodDeclaration, ctx: Context[F]): Unit =
    if (!ctx.config.filterMethod(method)) {
      val modifiers = method.getModifiers
      if (!Modifier.isAbstract(modifiers) && !Modifier.isNative(modifiers)
        && method.getBody != null
        && method.getBody.getStmts != null && method.getBody.getStmts.size() > 0) {

        val methodName = method.getName

        val prevLoc = ctx.currentLocation
        ctx.currentLocation = ctx.currentLocation.enterMethod(methodName, method.getBeginLine)
        var vec = before(method, ctx)

        try {
          //          val counter = new CounterVec[String]
          //          collectNodes(method.getBody.getStmts, counter)(ctx)
          //          val c1 = counter.count

          collectNodes(method.getBody.getStmts, vec)(ctx)
        } catch {
          case e: Exception => throw new RuntimeException(s"Could not travel though method ${ctx.currentLocation}", e)
        }
        vec = after(method, vec, ctx)
        ctx.vecWriter.write(vec)
        ctx.currentLocation = prevLoc
      }
    }

  def collectNodes[E <:Node](nodes: java.util.List[E], vec: CharacVec[F])(implicit ctx: Context[F]): Unit = {
    if (nodes != null && nodes.size > 0)
      for (n <- nodes)
        collectNode(n, vec)(ctx)
  }

  protected def collectNode(pnode: Node, vec: CharacVec[F])(implicit ctx: Context[F]): Unit = {
    val cfg = ctx.config
    if (!cfg.filterNode(pnode)) {
      if (ctx.config.skipNode(pnode)) {
        collectNodes(pnode.getChildrenNodes, vec)
      } else
        pnode match {
          case stmt: Statement =>
            if (!cfg.filterStmt(stmt)) {
              if (ctx.config.skipStmt(stmt))
                collectNodes(stmt.getChildrenNodes, vec)
              else
                collectStmt(stmt, vec)
            }
          // filter expr
          case expr: Expression =>
            if (!cfg.filterExpr(expr)) {
              if (cfg.skipExpr(expr))
                collectNodes(expr.getChildrenNodes, vec)
              else
                collectExpr(expr, vec)
            }

          // continue with new class
          case tp: ClassOrInterfaceDeclaration if !tp.isInterface =>
            classVisitor.visit(tp, ctx)

          case node => putNode(node, vec)
        }
    }
  }

  protected def collectExpr(expr: Expression, vec: CharacVec[F])(implicit ctx: Context[F]): Unit = expr match {
      // skip enclosed expr
    case e: EnclosedExpr => collectNodes(e.getChildrenNodes, vec)
    case ot => putNode(ot, vec)
  }

  protected def collectStmt(stmt: Statement, vec: CharacVec[F])(implicit ctx: Context[F]): Unit = stmt match {
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

  protected def putNode(node: Node, vec: CharacVec[F])(implicit ctx: Context[F]): Unit =
    node match {
      case call: MethodCallExpr =>
          addMethodCall(call, vec)
      case _ =>
        vec.put(findNodeName(node).asInstanceOf[F])
        collectNodes(node.getChildrenNodes, vec)
    }

  protected[codesniffer] def addMethodCall(call: MethodCallExpr, vec: CharacVec[F]): Unit = {
    vec.put("MethodCallExpr".asInstanceOf[F])
    val scope = BasicVGen.findCallee(call.getScope)
    val methodName = call.getName
    //          val lsExps = call.getArgs
    vec.funcCalls += (scope -> methodName)
  }

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
