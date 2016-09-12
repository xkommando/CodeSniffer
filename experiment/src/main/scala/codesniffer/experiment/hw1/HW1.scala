package codesniffer.experiment.hw1

import java.io.{File, PrintWriter}

import codesniffer.ASTHelper
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

    val methodVisitor = new BasicVecGen[String] {

      @inline
      override protected def collectStmt(stmt: Statement, vec: CharacVec[String])(implicit ctx: Context[String]): Unit = {
        if (!ctx.config.filterStmt(stmt)) {

          if (ctx.config.skipStmt(stmt))
            collectNodes(stmt.getChildrenNodes, vec)
          else {
            stmt match {
              // skip ExpressionStmt
              case est: ExpressionStmt =>
                collectNode(est.getExpression, vec)
              // skip BlockStmt
              case bst: BlockStmt =>
                collectNodes(bst.getStmts, vec)
                ////////////////////////////////////// part 2 code //////////////////////////////////////
              //            with length > 3 and are only used without !
              case ifs: IfStmt =>
                ifs.getCondition match {
                  case namexp: NameExpr if namexp.getName.length > 3=>
                    val outStr = s"${namexp.getName}  ${namexp.getBeginLine}"
                    println(outStr)
                    val thenst = ifs.getThenStmt
                    val nex_System = new NameExpr("System")
                    val nex_out = new NameExpr("out")
                    val nex_println = new NameExpr("println")
                    val faexp_sout = new FieldAccessExpr(nex_System, "out")
                    val soutpCall = new MethodCallExpr(faexp_sout, "println")
                    ASTHelper.addArgument(soutpCall, new StringLiteralExpr(outStr))

                    thenst match {
                      case bst: BlockStmt =>
                        ASTHelper.addStmt(bst, soutpCall)
                      case a: Statement =>
                        val bst = new BlockStmt()
                        ASTHelper.addStmt(bst, soutpCall)
                        ASTHelper.addStmt(bst, thenst)
                        ifs.setThenStmt(bst)
                      case _=>
                        println("error!!!!!!!!!!!!!!")
                        println(ifs)
                    }
                  case _=>
                }
//                val cond = ifs.getCondition
//                println(cond)
              //////////////////////////////////////////////////////////////////////////////////////////
              case _=>
            }

            if (ctx.config.filterStmt(stmt))
              return
            putNode(stmt, vec)
            matchInner(stmt, vec)
          }// no skip
        } // no filter
      }
    }

//    var before: (MethodDeclaration, Context[F])=> CharacVec[F] = BasicVecGen.newVec[F]
    methodVisitor.before =(m, ctx) => new NOPVec[String](location = null)

    val scanner = new SrcScanner(new Context[String](
      config = new DirScanConfig,
      currentLocation = null,
      data = null,
      indexer = new Indexer[String],
      vecWriter = new VecWriter[String]{
        override def write(vec: CharacVec[String]): Unit = {} // NOP
      }))

//    methodVisitor = new BasicVecGen[String]
    scanner.methodVisitor = methodVisitor
    scanner.classVisitor.methodVisitor = methodVisitor
    methodVisitor.classVisitor = scanner.classVisitor
    val cu = scanner.processFile(srcF)
    new PrintWriter(outPath) {
      write(cu.toString)
      close()
    }

  }

}
