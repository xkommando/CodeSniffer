package codesniffer.codefunnel

import java.io.{File, FileInputStream}
import java.sql.DriverManager

import codesniffer.api.body.MethodDeclaration
import codesniffer.api.expr.ThisExpr
import codesniffer.api.stmt.EmptyStmt
import codesniffer.api.visitor.VoidVisitorAdapter
import codesniffer.api.{CompilationUnit, Node}
import codesniffer.deckard.vgen.{Context, DirScanConfig, SrcScanner}
import codesniffer.deckard.{ClassScope, Location}
import codesniffer.java8.{CompilationUnitListener, Java8Lexer, Java8Parser}
import org.antlr.v4.runtime
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.atn.PredictionMode
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.slf4j.{LoggerFactory, Logger}

import scala.collection.convert.wrapAsScala._
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, StringBuilder}
import scala.util.Try

/**
  * Created by Bowen Cai on 2/24/2016.
  */
object MethodImport extends DBSupport {

  val LOG = LoggerFactory.getLogger(getClass)

  def main(args: Array[String]) {
//    importSRC(1, "D:\\__TEMP__\\eclipse-jdtcore\\src")
//    importSRC(2, "D:\\__TEMP__\\eclipse-ant\\src")
    importSRC(3, "D:\\__TEMP__\\j2sdk1.4.0-javax-swing\\src")
    importSRC(4, "D:\\__TEMP__\\netbeans-javadoc\\src")
  }

  def quoteTo(param: String): String = {
    val b = new scala.StringBuilder(param.length * 3 / 2)
    b append '''
    for (c <- param) c match {
      case ''' => b append ''' append '''
      case o => b append o
    }
    b append '''
    b.toString()
  }

  case class RowData(name:String, retType:String, funcSrc: String, loc:Location, tokens:Array[SToken])

  class InsertBuffer(val pojId:Int) {
    private val buf = new ArrayBuffer[RowData](256)

    def add(rd: RowData) = {
      buf += rd
      if (buf.length > 255)
        flush()
    }

    def flush(): Unit = {
      db.newSession {session => {
        val stmt = session.connection.createStatement()

        buf.foreach{rd=>
          val tks = rd.tokens.filter(_.getChannel == 0) // default (value == 0, usefull) tokens only, channel 1 is empty token, channel 2 is comment token
          val builder = new StringBuilder(tks.length * 70 + 250).append(
            s"""INSERT INTO \"procedure\"(\"name\", poj_id, \"file\", \"package\", \"class\", \"lines\", src_impl, token_count, token_seq)VALUES (
                '${rd.name}', $pojId, '${rd.loc.file}', '${rd.loc.scope.parent.toString}', '${rd.loc.scope.asInstanceOf[ClassScope].name}',
                INT4RANGE(${rd.loc.lineBegin}, ${rd.loc.lineEnd}), ${quoteTo(rd.funcSrc)}, ${tks.length}, ARRAY[
             """)
          val strB = tks.foldLeft(builder)((b: mutable.StringBuilder, tk: SToken) => {
            ////          CREATE TYPE lex_token AS("type" INT, "line" INT, "column" INT, "text" TEXT);
            ////-- insert into jtest(arr)values(ARRAY['(0, 15, 99, "public")'::lex_token, '(6, 154, 299, "class")'::lex_token])
            b.append(s""" (${tk.getType}, ${tk.getLine}, ${tk.getCharPositionInLine}, ${quoteTo(tk.getText)})::lex_token,""") ///! escape
            b
          }
          )
          strB.setCharAt(strB.length - 1, ']')
          strB.append(')')
//          println(strB)
          stmt.addBatch(strB.toString())
        }
        stmt.executeBatch()
      } // db session
      }// db session
      LOG.debug("Flushing: " + buf.length)
      buf.clear()
    }
  }

  def importSRC(pojId: Int, dir:String): Unit = {
    LOG.info("Booting...")
    super.boot()
    val config = new DirScanConfig
    config.filterFile = (f: File) => {
      val name = f.getName
      name.equals("package-info.java") || name.endsWith("Tests.java")
    }
    config.filterNode = (node: Node) => node.isInstanceOf[EmptyStmt] || node.isInstanceOf[ThisExpr]
    val context = new Context[Int](config, currentLocation = new Location(dir, 0, 0, null), data = null, indexer = null, vecWriter = null)
    val scanner = new SrcScanner[Int](context)
    var tokenSource: STokenSource = null
    var fileCount = 0
    var methodCount = 0

    val buf = new InsertBuffer(pojId)
    scanner.processFile = (src)=>{
      fileCount += 1
      val stream = new FileInputStream(src)
      val antlrStream = new runtime.ANTLRInputStream(stream)
      tokenSource = new STokenSource(
        new Java8Lexer(antlrStream).getAllTokens
          .filter(_.getChannel != 1)
          .zipWithIndex.map{case (t,i) =>{SToken(i, t.getType, t.getLine, t.getCharPositionInLine, t.getChannel, t.getStartIndex, t.getStopIndex, t.getText)}}.toArray)

      val cu = try {// Try { cannot handle not impl error
        val tokens = new CommonTokenStream(tokenSource)
        val parser = new Java8Parser(tokens)
        parser.getInterpreter.setPredictionMode(PredictionMode.SLL)
        val tree = parser.compilationUnit
        val walker = new ParseTreeWalker
        val listener = new CompilationUnitListener(tokens)
        walker.walk(listener, tree)
        listener.getCompilationUnit
      } catch {
        case e:Throwable => e.printStackTrace()
          null.asInstanceOf[CompilationUnit]
      }
      stream.close()
      if (cu != null) {
        try {
          scanner.context.currentLocation = scanner.context.currentLocation.copy(file = src.getPath.substring(dir.length))
          scanner.fileVisitor.visit(cu, context)
        } catch {
          case e: Throwable =>
            LOG.error(s"Could not travel though unit ${src.getPath}", e)
            e.printStackTrace()
        }
      }
    }
    class TestV extends VoidVisitorAdapter[Context[Int]] {
      override def visit(meDec: MethodDeclaration, ctx: Context[Int]): Unit = {

        methodCount += 1
        val ls = meDec.getBeginLine
        val cs = meDec.getBeginColumn
        val le = meDec.getEndLine
        val ce = meDec.getEndColumn
        val methodTokens = tokenSource.tokens.filter { t =>
          val l = t.getLine
          val c = t.getCharPositionInLine
          (l > ls || l == ls && c >= cs) && (l < le || l == le && c <= ce)
        }
//        LOG.debug(s"find method ${meDec.getName}  from ${ctx.currentLocation.file}\r\n token count: ${methodTokens.length} code lines: ${(le - ls)}")

        if (le - ls > 3) {
          buf.add(RowData(meDec.getName, meDec.getType.toString, meDec.toString,
            ctx.currentLocation.copy(lineBegin = ls, lineEnd = le),
            methodTokens))
        }

//        val doc = meDec.getDocComment
//        if (doc != null) {
//          println("doc c  " + doc.getContent)
//          val docC = doc.getAllContainedComments
//          if (docC != null && docC.size() > 0) {
//            println(" doc com sss   ")
//            docC.foreach(c => println(c.getContent + "   " + c.getComment.getContent))
//          }
//        }
//        // .get(0).getComment.getContent
//        println("return  " + meDec.getType.toString)
//        val annos = meDec.getAnnotations
//        if (annos != null && annos.size() > 0) {
//          println(" annotations:  ")
//          annos.foreach(a=>println(a.getName.getName + "   " + a.toString))
//        }
        //        println( fileCount + "   " + methodCount + " ============================>")
//        println(methodTokens + "   " + (le - ls))
//        println(ctx.currentLocation)
//        println(meDec)
//        println("<============================")
      }
    }
    scanner.methodVisitor = new TestV
    scanner.classVisitor.methodVisitor = scanner.methodVisitor

    scanner.scanDir(new File(dir), recursive = true)
    buf.flush()
  }

}
//def flush(): Unit = {
//      db.newSession {session => {
////        val ps = session.connection.prepareStatement(
//        "INSERT INTO \"procedure\"(\"name\", poj_id, \"file\", \"package\", \"class\", \"lines\", src_impl, token_seq, token_count)VALUES (?,?,?,?,?,?::INT4RANGE,?,?,?)"
////        )
//        val stmt = session.connection.createStatement()
//
//
//        buf.foreach{rd=>
//          val tks = rd.tokens
//          val builder = new StringBuilder(tks.length * 40 + 100).append("ARRAY[")
//
//          ps.setString(1, rd.name)
//          ps.setInt(2, pojId)
//          ps.setString(3, rd.loc.file)
//          rd.loc.scope match {
//            case ClassScope(nm, p) =>
//              ps.setString(4, p.toString)
//              ps.setString(5, nm)
//            case _ => ps.setString(4, "?")
//              ps.setString(5, "?")
//          }
//          val lines = "(" + rd.loc.lineBegin + ", " + rd.loc.lineEnd + ")"
//          ps.setString(6, lines)
//          ps.setString(7, rd.funcSrc)
//
////          CREATE TYPE lex_token AS("type" INT, "line" INT, "column" INT, "text" TEXT);
////-- insert into jtest(arr)values(ARRAY['(0, 15, 99, "public")'::lex_token, '(6, 154, 299, "class")'::lex_token])
//          val builder = new StringBuilder(tks.length * 40 + 100).append("ARRAY[")
//          val strB = tks.filter(_.getChannel == 0).foldLeft(builder)((b: StringBuilder, tk: SToken) => {
//            b.append("'(").append(tk.getType).append(",").append(tk.getLine).append(",").append(tk.getCharPositionInLine)
//              .append(",\"").append(tk.getText).append("\")'::lex_token,")
//          }
//          )
//          strB.setCharAt(strB.length - 1, ']')
////          ps.setArray(8, session.connection.createArrayOf("lex_token", rd.tokens.asInstanceOf[Array[AnyRef]]))
//          println(strB)
//          ps.setString(8, strB.toString())
//          ps.setInt(9, rd.tokens.length)
//          ps.addBatch()
//        }
//        println("insert " + ps.executeBatch())
//      }
//      }
//      buf.clear()
//    }