package codesniffer.vgen

import java.lang.reflect.Modifier
import java.util.Collections

import codesniffer.core._
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

  @BeanProperty var classVisitor: VoidVisitorAdapter[Context] = _

  override def visit (method: MethodDeclaration, ctx: Context): Unit = {
    val modifiers = method.getModifiers
    if (!Modifier.isAbstract(modifiers) && !Modifier.isNative(modifiers)
      && method.getBody != null
      && method.getBody.getStmts != null && method.getBody.getStmts.size() > 0) {

      val methodName = method.getName

      val prevLoc = ctx.currentLocation
      ctx.currentLocation = ctx.currentLocation.enterMethod(methodName, method.getBeginLine)

      val vec = new CharacVec[String](ctx.indexer, ctx.currentLocation, methodName, extractSignature(method), None)

      val stmts = method.getBody.getStmts
      for (stmt <- stmts)
        collectNode(stmt, vec)(ctx)

      ctx.vecWriter.write(vec)

      ctx.currentLocation = prevLoc
    }
  }

  protected def collectNode(pnode: Node, vec: CharacVec[String])(implicit ctx: Context): Unit =
    if (!ctx.config.NodeFilter(pnode)) {

      // skip ExpressionStmt
      val node = if (pnode.isInstanceOf[ExpressionStmt])
        pnode.asInstanceOf[ExpressionStmt].getExpression
      else pnode

      if (node.isInstanceOf[ClassOrInterfaceDeclaration]
          && !node.asInstanceOf[ClassOrInterfaceDeclaration].isInterface) {

        classVisitor.visit(node.asInstanceOf[ClassOrInterfaceDeclaration], ctx)

      } else {
//        case n: MethodCallExpr =>
//        case _ =>
//        node.getClass.getSimpleName
        vec.put(findNodeName(node))

        val children = node.getChildrenNodes
        if (children != null && children.size() > 0) {
          for (n <- children)
            collectNode(n, vec)
        }
      }
    }

  protected def findNodeName(node: Node): String = node match {
    case a: BooleanLiteralExpr =>
      if (node.asInstanceOf[BooleanLiteralExpr].getValue)
          "__BOOL_TRUE___"
      else "__BOOL_FALSE___"
    case a: PrimitiveType => a.asInstanceOf[PrimitiveType].getType.name()
    case a: BinaryExpr => a.asInstanceOf[BinaryExpr].getOperator.name()
    case a: UnaryExpr => a.asInstanceOf[UnaryExpr].getOperator.name()
    case _ => node.getClass.getSimpleName
  }

  protected def extractSignature(m: MethodDeclaration): MethodDescriptor = {

    val paramTypeNames = if (null == m.getParameters || m.getParameters.size() == 0) None
      else Some(m.getParameters.map{p=>extractTypeName(p.getType)}.toSeq)
    val annotationNames = if (null == m.getAnnotations || m.getAnnotations.size() == 0) None
      else Some(m.getAnnotations.map(_.getName.getName).toSeq)
    val throwNames = if (null == m.getThrows || m.getThrows.size() == 0) None
      else Some(m.getThrows.map(_.getName).toSeq)

    new MethodDescriptor(extractTypeName(m.getType),
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
      val arr = a.getArrayCount
      if (arr == 0) name
      else arr + "D-Array[" + name + "]"
    case _ : VoidType => "void"
    case _ : WildcardType => "Wildcard"
    case _  => "UNK"
  }

}
