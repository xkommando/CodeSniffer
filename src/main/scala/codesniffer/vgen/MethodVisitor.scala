package codesniffer.vgen

import java.lang.reflect.Modifier
import java.util.Collections

import codesniffer.core._
import com.github.javaparser.ast.Node
import com.github.javaparser.ast.`type`._
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.stmt.ExpressionStmt
import com.github.javaparser.ast.visitor.GenericVisitorAdapter
import scala.collection.convert.wrapAsScala._
/**
 * Created by Bowen Cai on 4/16/2015.
 */
class MethodVisitor extends GenericVisitorAdapter[Option[CharacVec], Context] {

  override def visit (method: MethodDeclaration, ctx: Context): Option[CharacVec] = {
    val modifiers = method.getModifiers
    if (!Modifier.isAbstract(modifiers) && !Modifier.isNative(modifiers)
      && method.getBody != null
      && method.getBody.getStmts != null && method.getBody.getStmts.size() > 0) {

      val methodName = method.getName

      ctx.location.enterMethod(methodName)
      val vec = new CharacVec(ctx, ctx.location.copy(), methodName, extractSignature(method))

      val stmts = method.getBody.getStmts
      for(stmt <- stmts)
        collectNode(stmt, vec)

      ctx.location.leave()
      Some(vec)
    } else None
  }

  protected def collectNode(pnode: Node, vec: CharacVec): Unit = if (!vec.context.config.NodeFilter(pnode)) {
    val node = if (pnode.isInstanceOf[ExpressionStmt])
      pnode.asInstanceOf[ExpressionStmt].getExpression
    else pnode

    vec.put(node)
    val children = node.getChildrenNodes
    if (children != null && children.size() > 0) {
      for (n <- children)
        collectNode(n, vec)
    }
  }

//  case class MethodSignature(typeName: String, // why not ref javaparser.Type? javaparser.Type is a node and may cause mem leak
//                             parameterTypes: Option[Array[String]],
//                             annotations: Option[Array[String]],
//                             throws: Option[Array[String]]

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
