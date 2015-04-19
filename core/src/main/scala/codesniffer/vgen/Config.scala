package codesniffer.vgen

import com.github.javaparser.ast.Node
import com.github.javaparser.ast.expr.Expression
import com.github.javaparser.ast.stmt.Statement


/**
 * Created by Bowen Cai on 4/12/2015.
 */
class Config {

  private def NOP[T](p:T):Boolean = false

  var stmtFilter: Statement=>Boolean = NOP[Statement]
  var expressionFilter: Expression=>Boolean = NOP[Expression]
  var NodeFilter: Node=>Boolean = NOP[Node]

}
