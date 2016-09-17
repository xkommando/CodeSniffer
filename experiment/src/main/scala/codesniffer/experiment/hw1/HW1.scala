package codesniffer.experiment.hw1

import java.io.{File, PrintWriter}

import codesniffer.ASTHelper
import codesniffer.api.Node
import codesniffer.api.expr._
import codesniffer.api.stmt._
import codesniffer.deckard._
import codesniffer.deckard.vgen._

/**
  * Created by superbow on 9/10/2016.
  */
object HW1 {

  def main(args: Array[String]): Unit = {
    if (args == null || args.length != 2) {
      println("Usage: <input java file>  <output java file>")
      return
    }
//    args.foreach(println)
    work(args(0), args(1))
  }

  def work(pathToSrc: String, outPath: String): Unit = {
    val srcF = new File(pathToSrc)
    require(srcF.exists() && srcF.canRead)

    val methodVisitor = new AbstractMethodVisitor[Unit] {
      def putNode(node: Node, ctx: Context[Unit]): Unit =
         node match {
          case ifs: IfStmt =>
            ifs.getCondition match {
              case namexp: NameExpr if namexp.getName.length > 3 =>
                val outStr = s"${namexp.getName}  ${namexp.getBeginLine}"
                println(outStr)
                val soutpCall = new MethodCallExpr(new FieldAccessExpr(new NameExpr("System"), "out"), "println")
                ASTHelper.addArgument(soutpCall, new StringLiteralExpr(outStr))

                val thenst = ifs.getThenStmt
                thenst match {
                  case bst: BlockStmt =>
                    ASTHelper.addStmt(bst, soutpCall)
                  case a: Statement =>
                    val bst = new BlockStmt()
                    ASTHelper.addStmt(bst, soutpCall)
                    ASTHelper.addStmt(bst, thenst)
                    ifs.setThenStmt(bst)
                  case _ =>
                    println("error!!!!!!!!!!!!!!")
                    println(ifs)
                }
              case _ => // non-if
            }
          case _ => // node type
        }
    }

    val scanner = new SrcScanner(new Context[Unit](
      config = new DirScanConfig,
      currentLocation = null,
      data = null,
      indexer = new Indexer[Unit],
      vecWriter = new VecWriter[Unit]{
        override def write(vec: CharacVec[Unit]): Unit = {} // NOP writer
      }))

    methodVisitor.classVisitor = scanner.classVisitor
    scanner.methodVisitor = methodVisitor
    scanner.classVisitor.methodVisitor = methodVisitor

    scanner.fileVisitor.after = (cu, ctx) =>
      new PrintWriter(outPath) {
        write(cu.toString)
        close()
      }

    scanner.processFile(srcF)
  }

}
