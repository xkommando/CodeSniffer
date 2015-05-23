package codesniffer.vgen

import com.github.javaparser.ast.body.{ClassOrInterfaceDeclaration, MethodDeclaration}
import com.github.javaparser.ast.visitor.{VoidVisitor, VoidVisitorAdapter}

import scala.beans.BeanProperty
import scala.collection.convert.wrapAsScala._

/**
 * Created by Bowen Cai on 4/13/2015.
 */
class ClassVisitor[F] extends VoidVisitorAdapter[Context[F]] {

  @BeanProperty var methodVisitor: VoidVisitor[Context[F]] = _

  import FileVisitor.NOP
  var before  = NOP[ClassOrInterfaceDeclaration, F]
  var after = NOP[ClassOrInterfaceDeclaration, F]

  override def visit(klass: ClassOrInterfaceDeclaration, ctx: Context[F]): Unit =
    if (!ctx.config.filterClass(klass)
      && !klass.isInterface) {

      // update location
      val prevLoc = ctx.currentLocation
      ctx.currentLocation = ctx.currentLocation.enterClass(klass.getName, klass.getBeginLine, klass.getEndLine)
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
