package codesniffer.vgen

import java.io.File

import com.github.javaparser.ast.body.{ClassOrInterfaceDeclaration, MethodDeclaration}
import com.github.javaparser.ast.{Node, PackageDeclaration}


/**
 * Created by Bowen Cai on 4/12/2015.
 */
class Config {

  private def NOP[T](p:T):Boolean = false

//  var scanRecursively: Boolean = true

  var filterFile: File=>Boolean = NOP[File]
  var filterFileName: String=>Boolean = NOP[String]
  var filterPackage: PackageDeclaration => Boolean = NOP[PackageDeclaration]
  var filterClass: ClassOrInterfaceDeclaration => Boolean = NOP[ClassOrInterfaceDeclaration]
  var filterMethod: MethodDeclaration => Boolean = NOP[MethodDeclaration]

  var filterNode: Node => Boolean = NOP[Node]
//  var filterStmt: Statement => Boolean = NOP[Statement]
//  var filterExpr: Expression => Boolean = NOP[Expression]

}
