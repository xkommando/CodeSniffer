package codesniffer.deckard.vgen

import codesniffer.api.stmt.{BlockStmt, Statement}
import codesniffer.api.visitor.{GenericVisitor, VoidVisitor}
import java.util.{List => JList}

/**
  * Created by superbow on 9/17/2016.
  */
object FinallyBlockStmt {
  def apply(bs: BlockStmt): FinallyBlockStmt
    = FinallyBlockStmt(bs.getBeginLine, bs.getBeginColumn, bs.getEndLine, bs.getEndColumn, bs.getStmts)
}
sealed case class FinallyBlockStmt(beginLine: Int, beginColumn: Int,
                                   endLine: Int, endColumn: Int,
                                   stmts: JList[Statement])

  extends Statement (beginLine, beginColumn, endLine, endColumn) {

  def this(stmts: JList[Statement]) = this(-1, -1, -1, -1, stmts)

  private var stmts_ : JList[Statement] = null

  setStmts(stmts)

  def accept[R, A](v: GenericVisitor[R, A], arg: A): R =  ???

  def accept[A](v: VoidVisitor[A], arg: A) = ???

  def getStmts  = stmts_

  def setStmts(stmts: JList[Statement]) {
    this.stmts_ = stmts
    setAsParentNodeOf(this.stmts)
  }
}
