package codesniffer.codefunnel.crawler

import java.io.{File, FileInputStream}

import codesniffer.api.body.MethodDeclaration
import codesniffer.api.expr.ThisExpr
import codesniffer.api.stmt.EmptyStmt
import codesniffer.api.visitor.VoidVisitorAdapter
import codesniffer.api.{CompilationUnit, Node}
import codesniffer.codefunnel.utils.{DBUtils, SToken, STokenSource}
import codesniffer.deckard.vgen.{Context, DirScanConfig, SrcScanner}
import codesniffer.deckard.{ClassScope, Location}
import codesniffer.java8.{CompilationUnitListener, Java8Lexer, Java8Parser}
import org.antlr.v4.runtime
import org.antlr.v4.runtime.atn.PredictionMode
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.antlr.v4.runtime.{CommonTokenStream, Token}
import org.slf4j.LoggerFactory

import scala.collection.convert.wrapAsScala._
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

case class RowData(name:String, retType:String, funcSrc: String, loc:Location, tokens:Array[SToken])

/**
  * @param pojId
  * @param mapToFS map file string to a real path on the file system (a relative path to an absolute path)
  */
class InsertionBuffer(val pojId:Int, mapToFS: String => String) {

  import DBUtils._

  private val buf = new ArrayBuffer[RowData](256)

  def add(rd: RowData) = this.synchronized {
    buf += rd
    if (buf.length > 255)
      flush()
  }

  def flush(): Unit = this.synchronized {
    if (buf.nonEmpty) try {
      import gplume.scala.jdbc.SQLAux.SQLInterpolation
      import gplume.scala.jdbc.SQLOperation._

      DBUtils.db.newSession { implicit session =>
        val fileMap = buf.map(_.loc.file).foldLeft(new mutable.HashMap[String, Int]()) { (mp, file) =>
          mp.put(file, -1)
          mp
        }
        // step 1, collect file -> id-in-DB
        fileMap.keys.foreach { file =>
          val idx = {
            val idx1 = file.lastIndexOf('/')
            val idx2 = file.lastIndexOf('\\')
            Math.max(0, Math.max(idx1, idx2))
          }
          val qname = quote(file.substring(idx + 1))
          val qDir = quote(file.substring(0, idx))

          val source = scala.io.Source.fromFile(mapToFS(file))
          val lines = try source.getLines().toArray finally source.close()
          val loc = lines.length
          val qcontent = quote(lines.mkString("\r\n"))
          source.close()

          val opId =
            sql"""SELECT id FROM src_file WHERE poj_id = $pojId
               AND "name" = $qname
               AND dir = $qDir
               AND loc = $loc
               AND "content" = $qcontent""".first(colInt)
          if (opId.isDefined) {
            fileMap.update(file, opId.get)
          } else {
            sql"""INSERT INTO src_file("name", dir, poj_id, loc, "content")VALUES(
               $qname, $qDir, $pojId, $loc, $qcontent)""".execute()
            fileMap.update(file, sql"SELECT LASTVAL()".first(colInt).get)
          }
        } // foreach file

        val sqlBuilder = new scala.StringBuilder(24000)
        val stmt = session.connection.createStatement()

        buf.foreach { rd =>
          val fileId = fileMap(rd.loc.file)
          // NOTE all comments will be filtered here
          // default (value == 0, usefull) tokens only, channel 1 is empty token, channel 2 is comment token
          val tks = rd.tokens.filter(_.getChannel == Token.DEFAULT_CHANNEL)
          sqlBuilder.append(
            s"""INSERT INTO \"procedure\"(\"name\", poj_id, srcfile_id, \"package\", \"class\", \"lines\",
              src_impl, return_t, token_count, token_seq)VALUES (
                '${rd.name}', $pojId, $fileId, '${rd.loc.scope.parent.toString}', '${rd.loc.scope.asInstanceOf[ClassScope].name}',
                INT4RANGE(${rd.loc.lineBegin}, ${rd.loc.lineEnd}), ${quote(rd.funcSrc)} , ${quote(rd.retType)}, ${tks.length}, ARRAY[""")
          val _str = tks.foldLeft(sqlBuilder) { (b, tk) =>
            ////          CREATE TYPE lex_token AS("type" INT, "line" INT, "column" INT, "text" TEXT);
            ////-- insert into jtest(arr)values(ARRAY['(0, 15, 99, "public")'::lex_token, '(6, 154, 299, "class")'::lex_token])
            b.append(s""" (${tk.getType}, ${tk.getLine}, ${tk.getCharPositionInLine}, ${quote(tk.getText)})::lex_token,""") ///! escape
          }
          sqlBuilder.setCharAt(_str.length - 1, ']')
          sqlBuilder.append(')')
          stmt.addBatch(sqlBuilder.toString())
          sqlBuilder.setLength(0)
        } // foreach
        stmt.executeBatch()
      } // db session
      MethodImport4J.LOG.debug("Flushing: " + buf.length)
      buf.clear()
    } catch {
      case e: Throwable => e.printStackTrace()
    }
  } // sync
}
/**
  * Created by Bowen Cai on 2/24/2016.
  */
object MethodImport4J {

  val LOG = LoggerFactory.getLogger(getClass)

  def main(args: Array[String]) {
//    importSRC(1, "D:\\repos\\eclipse-jdtcore")
//    importSRC(2, "D:\\repos\\eclipse-ant")
//    importSRC(3, "D:\\repos\\j2sdk1.4.0-javax-swing")
//    importSRC(4, "D:\\repos\\netbeans-javadoc")
    importSRC(5, "D:\\repos\\__temp")
  }


  def importSRC(pojId: Int, rootDir:String): Unit = {
    LOG.info("importing " + rootDir)
    val config = new DirScanConfig
    config.filterFile = (f: File) => {
      val name = f.getName
      name.equals("package-info.java") || name.endsWith("Tests.java")
    }
    config.filterNode = (node: Node) => node.isInstanceOf[EmptyStmt] || node.isInstanceOf[ThisExpr]
    val context = new Context[Int](config, currentLocation = new Location(rootDir, 0, 0, null), data = null, indexer = null, vecWriter = null)
    val scanner = new SrcScanner[Int](context)
    var tokenSource: STokenSource = null
    var fileCount = 0
    var methodCount = 0
//    file = src.getPath.substring(rootDir.length).replace("\\","/"))
    val buf = new InsertionBuffer(pojId, (relative:String)=> (rootDir  + "/" + relative).replace("\\", "/"))
    scanner.processFile = (src)=>{
      fileCount += 1
      val stream = new FileInputStream(src)
      val antlrStream = new runtime.ANTLRInputStream(stream)

      tokenSource = new STokenSource(
        new Java8Lexer(antlrStream).getAllTokens
          .filter(_.getChannel != 1) // channel 0 default(and useful); 1 blank; 2 comment
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
        case e:Throwable =>
          System.err.println(e.getClass.getSimpleName)
          null.asInstanceOf[CompilationUnit]
      } finally {
        stream.close()
      }
      if (cu != null) {
        try {
          scanner.context.currentLocation = scanner.context.currentLocation.copy(file = src.getPath.substring(rootDir.length + 1).replace("\\","/"))
          scanner.fileVisitor.visit(cu, context)
        } catch {
          case e: Throwable =>
            LOG.error(s"Could not travel though unit ${src.getPath}, " +
              s"exception: ${e.getClass.getSimpleName} ${e.getMessage}  cause: ${e.getCause}")
//            e.printStackTrace()
        }
      }
    } // process file

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
          (t.getChannel == Token.DEFAULT_CHANNEL) && (l > ls || l == ls && c >= cs) && (l < le || l == le && c <= ce)
        }
//        LOG.debug(s"find method ${meDec.getName}  from ${ctx.currentLocation.file}\r\n token count: ${methodTokens.length} code lines: ${(le - ls)}")
        if (le - ls > 3) {
//          buf.add(RowData(meDec.getName, meDec.getType.toString, meDec.toString,
//            ctx.currentLocation.copy(lineBegin = ls, lineEnd = le),
//            methodTokens))
        }

//        val com = meDec.getComment
//        println("comment: " + com)
//        val doc = meDec.getDocComment
//        if (doc != null) {
//          println("doc c  " + doc.getContent)
//          val docC = doc.getAllContainedComments
//          if (docC != null && docC.size() > 0) {
//            println(" doc com sss   ")
//            docC.foreach(c => println(c.getContent + "   " + c.getComment.getContent))
//          }
//        }
//        val annos = meDec.getAnnotations
//        if (annos != null && annos.size() > 0) {
//          println(" annotations:  ")
//          annos.foreach(a=>println(a.getName.getName + "   " + a.toString))
//        }
//                println( fileCount + "   " + methodCount + " ============================>")
//        println("<============================")
      }
    }
    scanner.methodVisitor = new TestV
    scanner.classVisitor.methodVisitor = scanner.methodVisitor

    scanner.scanDir(new File(rootDir), recursive = true)
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