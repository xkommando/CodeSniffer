package codesniffer.vgen

import java.lang.reflect.Modifier
import java.util.Collections

import codesniffer.core._
import com.github.javaparser.ast.Node
import com.github.javaparser.ast.`type`._
import com.github.javaparser.ast.body.{ClassOrInterfaceDeclaration, MethodDeclaration}
import com.github.javaparser.ast.expr.{UnaryExpr, BooleanLiteralExpr, BinaryExpr, LiteralExpr}
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
      val loc = ctx.currentLocation.enterMethod(methodName, method.getBeginLine)
      val vec = new CharacVec[String](ctx.indexer, loc, methodName, extractSignature(method))

//      vec.put("___MS_TYPE")

      val stmts = method.getBody.getStmts
      for (stmt <- stmts)
        collectNode(stmt, vec)(ctx)

      ctx.vecWriter.write(vec)
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
//        node.getClass.getSimpleName
        vec.put(findTypeName(node))

        val children = node.getChildrenNodes
        if (children != null && children.size() > 0) {
          for (n <- children)
            collectNode(n, vec)
        }
      }
    }

  protected def findTypeName(node: Node): String = node match {
    case a: BooleanLiteralExpr =>
      if (node.asInstanceOf[BooleanLiteralExpr].getValue)
          "__BOOL_TRUE___"
      else "__BOOL_FALSE___"
    case a: PrimitiveType => a.asInstanceOf[PrimitiveType].getType.name()
    case a: BinaryExpr => a.asInstanceOf[BinaryExpr].getOperator.name()
    case a: UnaryExpr => a.asInstanceOf[UnaryExpr].getOperator.name()
    case _ => node.getClass.getSimpleName
  }

  protected def extractSignature(m: MethodDeclaration): MethodSignature = {

    val paramTypeNames = if (null == m.getParameters || m.getParameters.size() == 0) None
      else Some(m.getParameters.map{p=>extractTypeName(p.getType)}.toArray)
    val annotationNames = if (null == m.getAnnotations || m.getAnnotations.size() == 0) None
      else Some(m.getAnnotations.map(_.getName.getName).toArray)
    val throwNames = if (null == m.getThrows || m.getThrows.size() == 0) None
      else Some(m.getThrows.map(_.getName).toArray)

    new MethodSignature(extractTypeName(m.getType),
                        paramTypeNames,
                        annotationNames,
                        throwNames)
  }

  protected def extractTypeName(tp: Type): String = tp match {
    case a : ClassOrInterfaceType => a.getName
    case a : PrimitiveType => a.getType.name()
    case a : ReferenceType => extractTypeName(a.getType)
    case _ : VoidType => "void"
    case _ : WildcardType => "Wildcard"
    case _  => "UNK"
  }

}
