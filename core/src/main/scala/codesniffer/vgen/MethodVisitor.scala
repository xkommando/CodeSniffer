package codesniffer.vgen

import java.lang.reflect.Modifier

import codesniffer.core._
import codesniffer.search.NodeCount
import com.github.javaparser.ast.Node
import com.github.javaparser.ast.`type`._
import com.github.javaparser.ast.body.{ClassOrInterfaceDeclaration, MethodDeclaration}
import com.github.javaparser.ast.expr._
import com.github.javaparser.ast.stmt.ExpressionStmt
import com.github.javaparser.ast.visitor.{VoidVisitorAdapter, VoidVisitor, GenericVisitorAdapter}
import scala.beans.BeanProperty
import scala.collection.convert.wrapAsScala._
/**
 * Created by Bowen Cai on 4/10/2015.
 */
class MethodVisitor extends VoidVisitorAdapter[Context] {

  @BeanProperty var classVisitor: VoidVisitor[Context] = _

  val NOP = (m: MethodDeclaration, v: CharacVec[_], ctx: Context)=>{}
  var before = NOP
  var after = NOP

  override def visit (method: MethodDeclaration, ctx: Context): Unit =
  if (!ctx.config.filterMethod(method)) {
    val modifiers = method.getModifiers
    if (!Modifier.isAbstract(modifiers) && !Modifier.isNative(modifiers)
      && method.getBody != null
      && method.getBody.getStmts != null && method.getBody.getStmts.size() > 0) {

      val methodName = method.getName

      val prevLoc = ctx.currentLocation
      ctx.currentLocation = ctx.currentLocation.enterMethod(methodName, method.getBeginLine)
      val vec = new CharacVec[String](ctx.indexer, ctx.currentLocation, methodName, extractSignature(method))
      before(method, vec, ctx)

      for (stmt <- method.getBody.getStmts)
        collectNode(stmt, vec)(ctx)

      ctx.vecWriter.write(vec)

      after(method, vec, ctx)
      ctx.currentLocation = prevLoc
    }
  }

  protected def collectNode(pnode: Node, vec: CharacVec[String])(implicit ctx: Context): Unit =
    if (!ctx.config.filterNode(pnode)) {

      // skip ExpressionStmt
      val node = pnode match {
        case stmt: ExpressionStmt => stmt.getExpression
        case _ => pnode
      }

      node match {
        case decl: ClassOrInterfaceDeclaration if !decl.isInterface =>
          classVisitor.visit(decl, ctx)
        case call: MethodCallExpr =>
          vec.put("MethodCallExpr")
          val scope = findCallee(call.getScope)
          val methodName = call.getName
//          val lsExps = call.getArgs
          vec.calls += (scope -> methodName)
        case _ =>
          vec.put(findNodeName(node))
          val children = node.getChildrenNodes
          if (children != null && children.size() > 0) {
            for (n <- children)
              collectNode(n, vec)
          }
      }
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
