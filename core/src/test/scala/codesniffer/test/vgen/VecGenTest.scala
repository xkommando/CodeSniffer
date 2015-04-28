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
import scala.util.Random

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

    val rand = new Random()
    val arr = (0 until 10).map(_=>rand.nextGaussian()).toArray
    val arr2 = (0 until 5).map(_=>arr).toArray
    println(arr2)

    val a1 = Array(1, 2, 3, 4, 5)
    val a2 = Array(5, 4, 3, 2, 1)
    var sum = 0
    for(i <- a1; j <- a2)
      sum += i * j
    println(sum)
  }

  @Test
  def t1: Unit = {

//    val cu = JavaParser.parse(new FileInputStream("D:\\__TEMP__\\AbstractAsyncTableRendering.java"), "UTF-8", false)
    val cu = JavaParser.parse(new FileInputStream("D:\\__TEMP__\\Src1.java"), "UTF-8", false)
//    val cu = new ASTParser("D:\\__TEMP__\\AbstractAsyncTableRendering.java", "UTF-8").CompilationUnit
    val pkg = cu.getPackage.getName
    val pkgName = if (pkg.isInstanceOf[QualifiedNameExpr])
      pkg.asInstanceOf[QualifiedNameExpr].getQualifier + "." + pkg.getName
    else pkg.getName

    val scope = new PackageScope(pkgName)
    val ctx = new Context(new Config(), new Location("Src1.java", 0, scope), new Indexer, new MemWriter)
//    val ctx = new Context(new Config(), new Location("AbstractAsyncTableRendering.java", 0, scope), new Indexer, new MemWriter)
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

      val ls = (ctx.vecWriter.asInstanceOf[MemWriter]).result()
      val totalNodes =
      println("Method count: " + ls.length)
      println("Node kind: " + ctx.indexer.maxIndex + 1)
      println("Total node count: " + ls.foldLeft(0){(count, vec)=>
        count + vec.count
      })

      //---------------------
      val sampleVec = ctx.vecWriter.asInstanceOf[MemWriter](1).asInstanceOf[CharacVec[String]]
      println(sampleVec.methodName + "   " + sampleVec.intern.count(_=>true))
      println(
        sampleVec.intern.foldLeft(0){(sum: Int, i)=>sum + i}
      )
    }
  }
}
