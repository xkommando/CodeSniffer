package codesniffer.test.vgen

import java.io.FileInputStream
import java.lang.reflect.Modifier

import codesniffer.core._
import codesniffer.vgen.{Context, Config, ClassVisitor, MethodVisitor}
import com.github.javaparser.{ASTParser, JavaParser}
import com.github.javaparser.ast.expr.{QualifiedNameExpr, ThisExpr}
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
  def t2 {
    val sb: Scope = null
    println(sb match {
      case a: PackageScope => "PackageScope"
      case a: ClassScope => "ClassScope"
      case a: MethodScope => "MethodScope"
      case _ => "null"
    }
    )

    val a = new Array[Int](10)
    a(0) = 1
    a(1) = 2
    a(2) = 3
    a(3) = 4
    a.take(4).foreach(println)
  }

  @Test
  def t1: Unit = {

    val cu = JavaParser.parse(new FileInputStream("D:\\__TEMP__\\AbstractAsyncTableRendering.java"), "UTF-8", false)
//    val cu = JavaParser.parse(new FileInputStream("D:\\__TEMP__\\Src1.java"), "UTF-8", false)
//    val cu = new ASTParser("D:\\__TEMP__\\AbstractAsyncTableRendering.java", "UTF-8").CompilationUnit
    val pkg = cu.getPackage.getName
    val pkgName = if (pkg.isInstanceOf[QualifiedNameExpr])
      pkg.asInstanceOf[QualifiedNameExpr].getQualifier + "." + pkg.getName
    else pkg.getName

    val scope = new PackageScope(pkgName)
//    val ctx = new Context(new Config(), new Location("Src1.java", 0, scope), new Indexer, new MemWriter)
    val ctx = new Context(new Config(), new Location("AbstractAsyncTableRendering.java", 0, scope), new Indexer, new MemWriter)
    ctx.config.NodeFilter = (node: Node)=> node.isInstanceOf[EmptyStmt] || node.isInstanceOf[ThisExpr]

    val mv = new MethodVisitor
    val cv = new ClassVisitor

    mv.setClassVisitor(cv)
    cv.setMethodVisitor(mv)

    if (cu.getTypes != null && cu.getTypes.size() > 0) {
      for (klass <- cu.getTypes if klass.isInstanceOf[ClassOrInterfaceDeclaration]) {
        cv.visit(klass.asInstanceOf[ClassOrInterfaceDeclaration], ctx)
      }

      println(ctx.indexer)
      ctx.vecWriter.asInstanceOf[MemWriter].foreach(println)

      val ls = (ctx.vecWriter.asInstanceOf[MemWriter]).toSeq.asInstanceOf[Seq[CharacVec[String]]]
      val totalNodes =
      println("Method count: " + ls.length)
      println("Node kind: " + ctx.indexer.maxIndex)
      println("Total node count: " + ls.foldLeft(0){(count, vec)=>
        count + vec.count
      })

      //---------------------
      val sampleVec = ctx.vecWriter.asInstanceOf[MemWriter](1).asInstanceOf[CharacVec[String]]
      println(sampleVec.methodName + "   " + sampleVec.vector.count(_=>true))
      println(
        sampleVec.vector.foldLeft(0){(sum: Int, i)=>sum + i}
      )
    }
  }
}
