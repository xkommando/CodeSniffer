package codesniffer.vgen

import Context
import com.github.javaparser.ast.body.{MethodDeclaration, ClassOrInterfaceDeclaration}
import com.github.javaparser.ast.visitor.{VoidVisitor, VoidVisitorAdapter}
import scala.beans.BeanProperty
import scala.collection.convert.wrapAsScala._

/**
 * Created by Bowen Cai on 4/13/2015.
 */
class ClassVisitor extends VoidVisitorAdapter[Context] {

  @BeanProperty var methodVisitor: VoidVisitor[Context] = _

  override def visit(klass: ClassOrInterfaceDeclaration, ctx: Context): Unit =
    if (!klass.isInterface) {
      val loc = ctx.currentLocation.enterClass(klass.getName, klass.getBeginLine)
      ctx.currentLocation = loc

      for (method <- klass.getMembers if method.isInstanceOf[MethodDeclaration]) {
        methodVisitor.visit(method.asInstanceOf[MethodDeclaration], ctx)
      }
    }


}
