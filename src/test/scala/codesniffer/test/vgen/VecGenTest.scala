package codesniffer.test.vgen

import java.io.FileInputStream
import java.lang.reflect.Modifier

import codesniffer.core._
import codesniffer.vgen.{Context, Config, ClassVisitor, MethodVisitor}
import com.github.javaparser.JavaParser
import com.github.javaparser.ast.expr.ThisExpr
import com.github.javaparser.ast.stmt.EmptyStmt
import com.github.javaparser.ast.{Node, CompilationUnit}
import com.github.javaparser.ast.body.{MethodDeclaration, ClassOrInterfaceDeclaration}
import org.junit.Test
import scala.collection.convert.wrapAsScala._
import scala.collection.mutable.ArrayBuffer
/**
 * Created by Bowen Cai on 4/13/2015.
 */
class VecGenTest {

  @Test
  def t1: Unit = {
    val mv = new MethodVisitor
    val cv = new ClassVisitor

    mv.setClassVisitor(cv)
    cv.setMethodVisitor(mv)

    val cu = JavaParser.parse(new FileInputStream("D:\\__TEMP__\\AbstractAsyncTableRendering.java"), "UTF-8", false)

    val scope = new ClassScope(cu.getPackage.getName.getName, null)
    val ctx = new Context(new Config(), new Location("AbstractAsyncTableRendering.java", scope, 0), new Indexer, new MemWriter)
    ctx.config.NodeFilter = (node: Node)=> node.isInstanceOf[EmptyStmt] || node.isInstanceOf[ThisExpr]

    if (cu.getTypes != null && cu.getTypes.size() > 0) {
      for (klass <- cu.getTypes if klass.isInstanceOf[ClassOrInterfaceDeclaration]) {
        cv.visit(klass.asInstanceOf[ClassOrInterfaceDeclaration], ctx)
      }

      ctx.vecWriter.asInstanceOf[MemWriter].foreach(println)
      val ls = ctx.vecWriter.asInstanceOf[MemWriter].result()

      val totalNodes = ls.foldLeft(0){(count, vec)=>
        count + vec.count
      }
      println(ctx.indexer.maxIndex)
      println(totalNodes)
//        //          print("  ")
//        //          println(klass.getName)
//        for (method <- klass.getMembers if method.isInstanceOf[MethodDeclaration]) {
//          test.visit
//        }
//      }
    }
  }
}
