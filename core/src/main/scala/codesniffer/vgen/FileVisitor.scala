package codesniffer.vgen

import codesniffer.core.PackageScope
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.expr.QualifiedNameExpr
import com.github.javaparser.ast.visitor.{VoidVisitor, VoidVisitorAdapter}

import scala.beans.BeanProperty
import scala.collection.convert.wrapAsScala._

/**
 * Created by Bowen Cai on 4/22/2015.
 */
object FileVisitor {
  // no operation
  def NOP[A]: (A, Context)=>Unit = (a: A, c:Context)=>{}
}
class FileVisitor extends VoidVisitorAdapter[Context] {

  @BeanProperty var classVisitor: VoidVisitor[Context] = _

  import FileVisitor.NOP
  var before = NOP[CompilationUnit]
  var after = NOP[CompilationUnit]

  override def visit(cu: CompilationUnit, ctx: Context): Unit = {
    // update location
    val pkgDec = cu.getPackage
    if (!ctx.config.filterPackage(pkgDec)) {

      val pkgName = if (pkgDec != null) {
        val nameExp = pkgDec.getName
        nameExp match {
          case exp: QualifiedNameExpr => exp.getQualifier + "." + exp.getName
          case _ => nameExp.getName
        }
      } else ""

      val nc = new PackageScope(pkgName)
      val prevLoc = ctx.currentLocation
      ctx.currentLocation = ctx.currentLocation.copy(scope = nc)
      before(cu, ctx)

      // search for methods
      if (cu.getTypes != null && cu.getTypes.size() > 0)
        for (klass <- cu.getTypes if klass.isInstanceOf[ClassOrInterfaceDeclaration])
          classVisitor.visit(klass.asInstanceOf[ClassOrInterfaceDeclaration], ctx)

      after(cu, ctx)
      ctx.currentLocation = prevLoc
    }
  }


}
