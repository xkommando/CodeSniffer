package codesniffer.test.vgen

import java.util

import scala.concurrent.ExecutionContext.Implicits.global
import java.io.{File, FileInputStream}
import java.lang.reflect.Modifier
import java.util.TreeMap

import scala.concurrent.duration._
import codesniffer.core._
import codesniffer.vgen._
import com.github.javaparser.{ASTParser, JavaParser}
import com.github.javaparser.ast.expr.{QualifiedNameExpr, ThisExpr}
import com.github.javaparser.ast.stmt.{Statement, SynchronizedStmt, EmptyStmt}
import com.github.javaparser.ast.{Node, CompilationUnit}
import com.github.javaparser.ast.body.{MethodDeclaration, ClassOrInterfaceDeclaration}
import org.junit.Test
import scala.collection.convert.wrapAsScala._
import scala.collection.mutable.ArrayBuffer
import scala.concurrent._
import scala.util.Random

/**
 * Created by Bowen Cai on 4/13/2015.
 */
class VecGenTest {

  @Test
  def t_filter_locks: Unit = {
    val path = "E:\\Dev\\scala\\TestScala\\src\\main\\java\\testsrc\\Src3.java"
    val _nodeFilter = (node: Node)=>node.isInstanceOf[EmptyStmt] || node.isInstanceOf[ThisExpr]
    val _libConfig = new Config
    _libConfig.filterDirName = (name: String) => (
      name.equals("package-info.java") // filter out package file
        || name.endsWith("Test.java") // filter out test file
      )
    _libConfig.filterNode = _nodeFilter
    val idx = new Indexer[String]
    val vecs = vgen(path, idx, _libConfig)
    println(idx)
    vecs.foreach(println)
  }

  def vgen(path: String, idx: Indexer[String], cfg: Config): MemWriter = {
    val dir = new File(path)
    require(dir.exists() && dir.canRead)

    val vs1 = new MemWriter
    val scanner1 = new SrcScanner(new Context(cfg, null, idx, vs1))
    // save exact source to vector, for manually check
    scanner1.methodVisitor.before =
      (m: MethodDeclaration, v: ArrayVec[_], ctx: Context)=> v.data = Some(m.toString)

    dir match {
      case where if where.isDirectory => scanner1.scanDir(where, recursive = true)
      case src if src.isFile => scanner1.scanFile(src)
    }
    vs1
  }

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
    val arr = (0 until 10).map(_ => rand.nextGaussian()).toArray
    val arr2 = (0 until 5).map(_ => arr).toArray
    println(arr2)

    val a1 = Array(1, 2, 3, 4, 5)
    val a2 = Array(5, 4, 3, 2, 1)
    var sum = 0
    for (i <- a1; j <- a2)
      sum += i * j
    println(sum)

    val vecCount = 10491
    val procCount = 4

    var right = vecCount
    val dprocCount = procCount.toDouble
    for(i <- 1 to procCount) {
      var left = vecCount - (math.sqrt(i / dprocCount) * vecCount).toInt
      for (j <- left until right) {
      }
      right = left
    }

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
    ctx.config.filterNode = (node: Node)=> node.isInstanceOf[EmptyStmt] || node.isInstanceOf[ThisExpr]

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
      val sampleVec = ctx.vecWriter.asInstanceOf[MemWriter](1).asInstanceOf[ArrayVec[String]]
      println(sampleVec.methodName + "   " + sampleVec.intern.count(_=>true))
      println(
        sampleVec.intern.foldLeft(0){(sum: Int, i)=>sum + i}
      )
    }
  }
}
