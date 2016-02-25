import java.io.{File, FileInputStream}

import codesniffer.api.body.MethodDeclaration
import codesniffer.api.visitor.VoidVisitorAdapter
import codesniffer.codefunnel.{_STokenSource, STokenSource, SToken}
import codesniffer.deckard.Location
import codesniffer.deckard.vgen._
import codesniffer.java8.{CompilationUnitListener, Java8Lexer, Java8Parser}
import org.antlr.v4.runtime
import org.antlr.v4.runtime.{ListTokenSource, Token, CommonTokenStream}
import org.antlr.v4.runtime.atn.PredictionMode
import org.antlr.v4.runtime.tree.ParseTreeWalker

import scala.collection.convert.wrapAsScala._
import scala.collection.convert.wrapAsJava._




/**
  * Created by Bowen Cai on 2/19/2016.
  */
object MTest {

  def main(args: Array[String]) {

    val src1 = "D:\\__jvm\\spring-framework-master\\spring-webmvc\\src\\test\\java\\org\\springframework\\web\\servlet\\mvc\\annotation\\ServletAnnotationControllerTests.java"
    val src2 = "D:\\__jvm\\spring-framework-master\\spring-aop\\src\\main\\java\\org\\springframework\\aop\\framework\\ObjenesisCglibAopProxy.java"
    val src3 = "D:\\ObjenesisCglibAopProxy.java"
    val src4 = "D:\\__jvm\\__temp\\AbstractIndexer.java"
    val src5 = "D:\\__jvm\\__temp\\ArrayAccess.java"
    val srcName = src4
    val src = new File(srcName)
    val stream = new FileInputStream(src)
    val antlrStream = new runtime.ANTLRInputStream(stream)
//    val lex = new Java8Lexer(antlrStream)
//    val ts = new STokenSource(
//      new Java8Lexer(antlrStream).getAllTokens.filter(_.getChannel != 1).zipWithIndex.map{case (t,i) =>{
//        SToken(i, t.getType, t.getLine, t.getCharPositionInLine, t.getChannel, t.getStartIndex, t.getStopIndex, t.getText)}}.toArray)

    val _tokens = new Java8Lexer(antlrStream).getAllTokens.filter(_.getChannel != 1).zipWithIndex.map{case (t,i) =>{
      SToken(i, t.getType, t.getLine, t.getCharPositionInLine, t.getChannel, t.getStartIndex, t.getStopIndex, t.getText)}}
//      SToken(i, t.getType, t.getLine, t.getCharPositionInLine, t.getChannel, t.getText)}}
    val ts = new _STokenSource(_tokens.toArray)
    val cu = try {

//      val hashSet = new mutable.HashSet[Int]()
//      val ks = collectionAsScalaIterable(tokens.getTokens)
//      lex.getAllTokens.filter(_.getChannel == 0).foreach(t => {
//        println(t.getLine + "   " + t.getCharPositionInLine + "   " + "  getChannel " + t.getChannel + "   " + t.getText)
//      }
//      )
//      ts.tokens.foreach(println)
      val tokens = new CommonTokenStream(ts)
//      val tokens = new CommonTokenStream(lex)
      val parser = new Java8Parser(tokens)

      parser.getInterpreter.setPredictionMode(PredictionMode.SLL)
      val tree = parser.compilationUnit
      val walker = new ParseTreeWalker

      // Fills out the compilationUnit object
      val listener = new CompilationUnitListener(tokens)
      walker.walk(listener, tree)
      listener.getCompilationUnit
    }
    stream.close()

    class TestV extends VoidVisitorAdapter[Context[Int]] {
      override def visit(method: MethodDeclaration, nil: Context[Int]): Unit = {
        val ls = method.getBeginLine
        val cs = method.getBeginColumn
        val le = method.getEndLine
        val ce = method.getEndColumn
        println((ls, cs, le, ce))
//        ts.tokens.filter{t=>
//          val l = t.getLine
//          val c = t.getCharPositionInLine
//          (l > ls || l == ls && c >= cs) && (l < le || l == le && c <= ce)
//        }.foreach{t=>
//          print(t + "  ")
//        }
        println(method)
        //    println(Serialization.writePretty(method)(DefaultFormats))
        //    println(JSON.toJSONString(method, new Array[SerializeFilter](0)))
//        print("==>" + method.getBeginLine +"  " + method.getBeginColumn)
//        println("  " + method.getEndLine +"  " + method.getEndColumn)

      }
    }

    val fv = new FileVisitor[Int]
    val cv = new ClassVisitor[Int]
    fv.classVisitor = cv
    val mv = new TestV
    cv.methodVisitor = mv
    val ctx = new Context[Int](new DirScanConfig, currentLocation = new Location(srcName, 0, 0, null), data = null, indexer = null, vecWriter = null)
    fv.visit(cu, ctx)
    System.out.flush()
  }
}
