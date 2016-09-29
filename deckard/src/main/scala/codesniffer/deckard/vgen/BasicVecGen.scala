package codesniffer.deckard.vgen

import java.lang.reflect.Modifier

import codesniffer.api.Node
import codesniffer.api.`type`._
import codesniffer.api.body.{ClassOrInterfaceDeclaration, MethodDeclaration}
import codesniffer.api.expr._
import codesniffer.api.stmt._
import codesniffer.api.visitor.{GenericVisitor, VoidVisitor, VoidVisitorAdapter}
import codesniffer.core._
import codesniffer.deckard.{AbstractMethodVisitor, ArrayVec, CharacVec, MethodDescriptor}

import scala.beans.BeanProperty
import scala.collection.convert.wrapAsScala._

/**
 *  generate vector top-down
 *
 * Created by Bowen Cai on 4/10/2015.
 */
object BasicVecGen {

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

class BasicVecGen[F] extends AbstractMethodVisitor[F] {

  before = (methodDec, ctx) => {
    ctx.data = Some(new ArrayVec[F](ctx.indexer, ctx.currentLocation,
      methodDec.getName, BasicVecGen.extractSignature(methodDec)).asInstanceOf[CharacVec[F]])
  }

  after = (methodDec, ctx) => {
    val vec = ctx.data.get.asInstanceOf[CharacVec[F]]
    ctx.vecWriter.write(vec)
  }

  override def putNode(node: Node,  ctx: Context[F]): Unit = {
    val vec = ctx.data.get.asInstanceOf[CharacVec[F]]
    node match {
      case call: MethodCallExpr =>
        addMethodCall(call, vec)(ctx)
      case _ =>
        vec.put(findNodeName(node).asInstanceOf[F])
        //        println(node)
        //        if (node.getChildrenNodes != null) {
        //          println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        //          println(node.getChildrenNodes)
        //        }
        collectNodes(node.getChildrenNodes, ctx) // useless, delete
    }
  }

  @inline
  protected[codesniffer] def addMethodCall(call: MethodCallExpr, vec: CharacVec[F])(implicit ctx: Context[F]): Unit = {
    vec.put(call.getClass.getSimpleName.asInstanceOf[F])
    val scope = call.getScope // scope is the caller, e.g., in "System.out.println", "System.out" is the scope
    if (scope != null) {
      collectExpr(scope, ctx)
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
      if (arr == 0) "___ReferenceType___"
      else "___ArrayType___"
    case _ => node.getClass.getSimpleName
  }


}
