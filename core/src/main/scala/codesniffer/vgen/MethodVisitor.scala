package codesniffer.vgen

import java.lang.reflect.Modifier

import codesniffer.core._
import com.github.javaparser.ast.Node
import com.github.javaparser.ast.`type`._
import com.github.javaparser.ast.body.{ClassOrInterfaceDeclaration, MethodDeclaration}
import com.github.javaparser.ast.expr._
import com.github.javaparser.ast.stmt.{BlockStmt, ExpressionStmt, Statement}
import com.github.javaparser.ast.visitor.{VoidVisitor, VoidVisitorAdapter}

import scala.beans.BeanProperty
import scala.collection.convert.wrapAsScala._
/**
 * Created by Bowen Cai on 4/10/2015.
 */
class MethodVisitor extends VoidVisitorAdapter[Context] {

  @BeanProperty var classVisitor: VoidVisitor[Context] = _

  val NOP = (m: MethodDeclaration, v: ArrayVec[_], ctx: Context) => {}
  var before = NOP
  var after = NOP

  override def visit(method: MethodDeclaration, ctx: Context): Unit =
    if (!ctx.config.filterMethod(method)) {
      val modifiers = method.getModifiers
      if (!Modifier.isAbstract(modifiers) && !Modifier.isNative(modifiers)
        && method.getBody != null
        && method.getBody.getStmts != null && method.getBody.getStmts.size() > 0) {

        val methodName = method.getName

        val prevLoc = ctx.currentLocation
        ctx.currentLocation = ctx.currentLocation.enterMethod(methodName, method.getBeginLine)
        val vec = new ArrayVec[String](ctx.indexer, ctx.currentLocation, methodName, extractSignature(method))
        before(method, vec, ctx)

        try {
//          val counter = new CounterVec[String]
//          collectNodes(method.getBody.getStmts, counter)(ctx)
//          val c1 = counter.count

          collectNodes(method.getBody.getStmts, vec)(ctx)
        } catch {
          case e: Exception => throw new RuntimeException(s"Could not travel though method ${ctx.currentLocation}", e)
        }
        ctx.vecWriter.write(vec)
        after(method, vec, ctx)
        ctx.currentLocation = prevLoc
      }
    }


  protected def collectNodes[E <:Node](nodes: java.util.List[E], vec: CharacVec[String])(implicit ctx: Context): Unit = {
    if (nodes != null && nodes.size > 0)
      for (n <- nodes)
        collectNode(n, vec)(ctx)
  }

  protected def collectNode(pnode: Node, vec: CharacVec[String])(implicit ctx: Context): Unit = {
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
                continue(expr, vec)
            }

          // continue with new class
          case tp: ClassOrInterfaceDeclaration if !tp.isInterface =>
            classVisitor.visit(tp, ctx)

          case node => continue(node, vec)
        }
    }
  }

  protected def collectStmt(stmt: Statement, vec: CharacVec[String])(implicit ctx: Context): Unit = stmt match {
    // skip ExpressionStmt
    case est: ExpressionStmt =>
      collectNode(est.getExpression, vec)
    // skip BlockStmt
    case bst: BlockStmt =>
      collectNodes(bst.getStmts, vec)
    // filter others stmt
    case fst =>
      if (!ctx.config.filterStmt(fst))
        continue(fst, vec)
  }

  protected def continue(node: Node, vec: CharacVec[String])(implicit ctx: Context): Unit =
    node match {
      case call: MethodCallExpr =>
          addMethodCall(call, vec)
      case _ =>
        vec.put(findNodeName(node))
        collectNodes(node.getChildrenNodes, vec)
    }

  protected def addMethodCall(call: MethodCallExpr, vec: CharacVec[String]): Unit = {
    vec.put("MethodCallExpr")
    val scope = findCallee(call.getScope)
    val methodName = call.getName
    //          val lsExps = call.getArgs
    vec.funcCalls += (scope -> methodName)
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

  protected def findNodeName(node: Node): String = node match {
    case a: BooleanLiteralExpr =>
      if (a.getValue) "__BOOL_TRUE___"
      else "__BOOL_FALSE___"
    case a: PrimitiveType => a.getType.name()
    case a: BinaryExpr => a.getOperator.name()
    case a: UnaryExpr => a.getOperator.name()
    case _ => node.getClass.getSimpleName
  }

  protected def extractSignature(m: MethodDeclaration): MethodDescriptor = {

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

  protected def extractTypeName(tp: Type): String = tp match {
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

}
