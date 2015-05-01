package codesniffer.vgen

import java.io.File

import com.github.javaparser.ast.{PackageDeclaration, Node}
import com.github.javaparser.ast.body.{MethodDeclaration, ClassOrInterfaceDeclaration}
import com.github.javaparser.ast.expr.Expression
import com.github.javaparser.ast.stmt.Statement


/**
 * Created by Bowen Cai on 4/12/2015.
 */
class Config {

  private def NOP[T](p:T):Boolean = false

  var filterFile: File=>Boolean = NOP[File]
  var filterFileName: String=>Boolean = NOP[String]
  var filterPackage: PackageDeclaration => Boolean = NOP[PackageDeclaration]
  var filterClass: ClassOrInterfaceDeclaration => Boolean = NOP[ClassOrInterfaceDeclaration]
  var filterMethod: MethodDeclaration => Boolean = NOP[MethodDeclaration]

  var filterNode: Node => Boolean = NOP[Node]
  var filterStmt: Statement => Boolean = NOP[Statement]
  var filterExpr: Expression => Boolean = NOP[Expression]

}
