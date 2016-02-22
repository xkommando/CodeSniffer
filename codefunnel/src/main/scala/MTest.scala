import java.io.{FileInputStream, File}

import codesniffer.api.body.MethodDeclaration
import codesniffer.api.visitor.VoidVisitorAdapter
import codesniffer.deckard.Location
import codesniffer.deckard.vgen._
import codesniffer.java8.{CompilationUnitListener, Java8Parser, Java8Lexer}
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.{SerializeFilter, SerializerFeature}
import net.liftweb.json.{DefaultFormats, Serialization}
import org.antlr.v4.runtime
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.atn.PredictionMode
import org.antlr.v4.runtime.tree.ParseTreeWalker
import scala.collection.convert.decorateAsScala._
import scala.collection.convert.wrapAsScala._
import scala.collection.mutable


class TestV extends VoidVisitorAdapter[Context[Int]] {
  override def visit(method: MethodDeclaration, nil: Context[Int]): Unit = {
//    println(Serialization.writePretty(method)(DefaultFormats))

    println(JSON.toJSONString(method, new Array[SerializeFilter](0)))
  }
}

/**
  * Created by Bowen Cai on 2/19/2016.
  */
object MTest {


  def main(args: Array[String]) {

    val fv = new FileVisitor[Int]
    val cv = new ClassVisitor[Int]
    fv.classVisitor = cv
    val mv = new TestV
    cv.methodVisitor = mv

    val src1 = "D:\\__jvm\\spring-framework-master\\spring-webmvc\\src\\test\\java\\org\\springframework\\web\\servlet\\mvc\\annotation\\ServletAnnotationControllerTests.java"
    val src2 = "D:\\__jvm\\spring-framework-master\\spring-aop\\src\\main\\java\\org\\springframework\\aop\\framework\\ObjenesisCglibAopProxy.java"
    val src3 = "D:\\ObjenesisCglibAopProxy.java"
    val srcName = src3
    val src = new File(srcName)
    val stream = new FileInputStream(src)

    val cu = try {

      val antlrStream = new runtime.ANTLRInputStream(stream)
      val lex = new Java8Lexer(antlrStream)
      val tokens = new CommonTokenStream(lex)
      println(tokens.getNumberOfOnChannelTokens)
//lex.getVocabulary.getLiteralName()
      val hashSet = new mutable.HashSet[Int]()
      tokens.get(0, 198).foreach(tk=>{
        hashSet += tk.getType
        println(tk.getLine + " " + tk.getStartIndex + "  " + tk.getTokenIndex  + "  " + tk.getText)
      })
      val vo = lex.getVocabulary
      hashSet.map(tp=>(vo.getDisplayName(tp), vo.getLiteralName(tp), vo.getSymbolicName(tp))).foreach(println)

      //printTokens(lex);
      val parser = new Java8Parser(tokens)

      parser.getInterpreter.setPredictionMode(PredictionMode.SLL)
      val tree = parser.compilationUnit
      val walker = new ParseTreeWalker

      // Fills out the compilationUnit object
      val listener = new CompilationUnitListener(tokens)
      walker.walk(listener, tree)
      listener.getCompilationUnit
    }
//    val ctx = new Context[Int](new Config, currentLocation = new Location(srcName, 0, 0, null), data = null, indexer = null, vecWriter = null)
//    fv.visit(cu, ctx)
  }
}
