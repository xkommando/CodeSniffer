package codesniffer.deckard.vgen

import java.io.File

import codesniffer.api.body.{ClassOrInterfaceDeclaration, MethodDeclaration}
import codesniffer.api.expr.Expression
import codesniffer.api.stmt.Statement
import codesniffer.api.{Node, PackageDeclaration}


/**
 * Created by Bowen Cai on 4/12/2015.
 */
class DirScanConfig {

  // no operation
  private def NOP[T](p:T):Boolean = false

  var filterFile: File=>Boolean = NOP[File]
  var filterDirName: String=>Boolean = NOP[String] //(name: String) => !name.endsWith(".java")
  var filterPackage: PackageDeclaration => Boolean = NOP[PackageDeclaration]
  var filterClass: ClassOrInterfaceDeclaration => Boolean = NOP[ClassOrInterfaceDeclaration]
  var filterMethod: MethodDeclaration => Boolean = NOP[MethodDeclaration]

  // filter out one and its children
  var filterNode: Node => Boolean = NOP[Node]
  // skip this one, continue with its children
  var skipNode: Node => Boolean = NOP[Node]

  var filterStmt: Statement => Boolean = NOP[Statement]
  var skipStmt: Statement => Boolean = NOP[Statement]
  var filterExpr: Expression => Boolean = NOP[Expression]
  var skipExpr: Expression => Boolean = NOP[Expression]

  var distThreshold = 18 // default weight 1

}
