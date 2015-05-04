package codesniffer.vgen

import com.github.javaparser.ast.body.{MethodDeclaration, ClassOrInterfaceDeclaration}
import com.github.javaparser.ast.visitor.{VoidVisitor, VoidVisitorAdapter}
import scala.beans.BeanProperty
import scala.collection.convert.wrapAsScala._

/**
 * Created by Bowen Cai on 4/13/2015.
 */
class ClassVisitor extends VoidVisitorAdapter[Context] {

  @BeanProperty var methodVisitor: VoidVisitor[Context] = _

  import FileVisitor.NOP
  var before  = NOP[ClassOrInterfaceDeclaration]
  var after = NOP[ClassOrInterfaceDeclaration]

  override def visit(klass: ClassOrInterfaceDeclaration, ctx: Context): Unit =
    if (!ctx.config.filterClass(klass)
      && !klass.isInterface) {

      // update location
      val prevLoc = ctx.currentLocation
      ctx.currentLocation = ctx.currentLocation.enterClass(klass.getName, klass.getBeginLine)
      before(klass, ctx)

      for (member <- klass.getMembers) member match {
        case method: MethodDeclaration => methodVisitor.visit(method, ctx)
        case classDef: ClassOrInterfaceDeclaration => this.visit(classDef, ctx)
        case _ =>
      }

      after(klass, ctx)
      // leave this class scope
      ctx.currentLocation = prevLoc
    }


}
