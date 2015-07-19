//package codesniffer.test.vgen
//
//import java.util
//
//import codesniffer.core.MemWriter
//import codesniffer.vgen.Config
//
//import scala.concurrent.ExecutionContext.Implicits.global
//import java.io.{File, FileInputStream}
//import java.lang.reflect.Modifier
//import java.util.TreeMap
//
//import scala.concurrent.duration._
//import codesniffer.core._
//import codesniffer.vgen._
//import codesniffer.api._;
//import codesniffer.api.body._;
//import codesniffer.api.comments._;
//import codesniffer.api.expr._;
//import codesniffer.api.stmt._;
//import codesniffer.api.`type`._;
//import codesniffer.api.visitor._;
//
//import java.util._;
//import org.junit.Test
//import scala.collection.convert.wrapAsScala._
//import scala.collection.mutable.ArrayBuffer
//import scala.concurrent._
//import scala.util.Random
//
///**
// * Created by Bowen Cai on 4/13/2015.
// */
//class VecGenTest {
//
//  @Test
//  def testSlicer: Unit ={
//    val path = "E:\\Dev\\scala\\TestScala\\src\\main\\java\\testsrc\\Src3.java"
////    val path = "D:\\__TEMP__\\src\\ClassReader.java"
//    val cfg = new Config
//    cfg.filterDirName = (name: String) => (
//      name.equals("package-info.java") // filter out package file
//        || name.endsWith("Test.java") // filter out test file
//      )
//    cfg.filterNode = (node: Node)=>node.isInstanceOf[EmptyStmt] || node.isInstanceOf[ThisExpr]
//    val idx = new Indexer[String]
//
//    val dir = new File(path)
//    require(dir.exists() && dir.canRead)
//
//    val vecList = new MemWriter[String]
//    val scanner1 = new SlicerScanner[String](new Context(cfg, null, null, idx, vecList))
//
//    scanner1.methodVisitor.lowerBound = 10
//    scanner1.methodVisitor.upperBound = 16
//
//    dir match {
//      case where if where.isDirectory => scanner1.scanDir(where, recursive = true)
//      case src if src.isFile => scanner1.scanFile(src)
//    }
//    println(vecList.size)
//    val len = scanner1.context.indexer.maxIndex
//    vecList.foreach{e=>
//      val a = e.asInstanceOf[ArrayVec[String]].vector
//      for (i <- 0 until len)
//        print(a(i) + " ")
//
//      println()
//      println(e.data.get)
//      println()
//    }
//
//  }
//
//  @Test
//  def t_filter_locks: Unit = {
//    val path = "E:\\Dev\\scala\\TestScala\\src\\main\\java\\testsrc\\Src3.java"
//    val _nodeFilter = (node: Node)=>node.isInstanceOf[EmptyStmt] || node.isInstanceOf[ThisExpr]
//    val _libConfig = new Config
//    _libConfig.filterDirName = (name: String) => (
//      name.equals("package-info.java") // filter out package file
//        || name.endsWith("Test.java") // filter out test file
//      )
//    _libConfig.filterNode = _nodeFilter
//    val idx = new Indexer[String]
//    val vecs = vgen(path, idx, _libConfig)
//    println(idx)
//    vecs.foreach(println)
//  }
//
//  def vgen(path: String, idx: Indexer[String], cfg: Config): MemWriter[String] = {
//    val dir = new File(path)
//    require(dir.exists() && dir.canRead)
//
//    val vs1 = new MemWriter[String]
//    val scanner1 = new SrcScanner(new Context(cfg, null, null, idx, vs1))
//    // save exact source to vector, for manually check
//    scanner1.methodVisitor.before =
//      (m: MethodDeclaration, ctx: Context[String])=> {
//      val v = new CounterVec[String](ctx.currentLocation)
//      v.data = Some(m.toString)
//      v
//    }
//
//    dir match {
//      case where if where.isDirectory => scanner1.scanDir(where, recursive = true)
//      case src if src.isFile => scanner1.scanFile(src)
//    }
//    vs1
//  }
//
//  @Test
//  def t2 {
//    val sb: Scope = null
//    println(sb match {
//      case a: PackageScope => "PackageScope"
//      case a: ClassScope => "ClassScope"
//      case a: MethodScope => "MethodScope"
//      case _ => "null"
//    }
//    )
//
//    val a = new Array[Int](10)
//    a(0) = 1
//    a(1) = 2
//    a(2) = 3
//    a(3) = 4
//    a.take(4).foreach(println)
//
//    val rand = new Random()
//    val arr = (0 until 10).map(_ => rand.nextGaussian()).toArray
//    val arr2 = (0 until 5).map(_ => arr).toArray
//    println(arr2)
//
//    val a1 = Array(1, 2, 3, 4, 5)
//    val a2 = Array(5, 4, 3, 2, 1)
//    var sum = 0
//    for (i <- a1; j <- a2)
//      sum += i * j
//    println(sum)
//
//    val vecCount = 10491
//    val procCount = 4
//
//    var right = vecCount
//    val dprocCount = procCount.toDouble
//    for(i <- 1 to procCount) {
//      var left = vecCount - (math.sqrt(i / dprocCount) * vecCount).toInt
//      for (j <- left until right) {
//      }
//      right = left
//    }
//
//  }
//
//  @Test
//  def t1: Unit = {
//
////    val cu = JavaParser.parse(new FileInputStream("D:\\__TEMP__\\AbstractAsyncTableRendering.java"), "UTF-8", false)
//    val cu = JavaParser.parse(new FileInputStream("D:\\__TEMP__\\Src1.java"), "UTF-8", false)
////    val cu = new ASTParser("D:\\__TEMP__\\AbstractAsyncTableRendering.java", "UTF-8").CompilationUnit
//    val pkg = cu.getPackage.getName
//    val pkgName = if (pkg.isInstanceOf[QualifiedNameExpr])
//      pkg.asInstanceOf[QualifiedNameExpr].getQualifier + "." + pkg.getName
//    else pkg.getName
//
//    val scope = new PackageScope(pkgName)
//    val ctx = new Context(new Config(), new Location("Src1.java", 0, 0, scope), null, new Indexer, new MemWriter)
////    val ctx = new Context(new Config(), new Location("AbstractAsyncTableRendering.java", 0, scope), new Indexer, new MemWriter)
//    ctx.config.filterNode = (node: Node)=> node.isInstanceOf[EmptyStmt] || node.isInstanceOf[ThisExpr]
//
//    val mv = new BasicVecGen
//    val cv = new ClassVisitor
//
//    mv.setClassVisitor(cv)
//    cv.setMethodVisitor(mv)
//
//    if (cu.getTypes != null && cu.getTypes.size() > 0) {
//      for (klass <- cu.getTypes if klass.isInstanceOf[ClassOrInterfaceDeclaration]) {
//        cv.visit(klass.asInstanceOf[ClassOrInterfaceDeclaration], ctx)
//      }
//
//      println(ctx.indexer)
//      ctx.vecWriter.asInstanceOf[MemWriter[String]].foreach(println)
//
//      val ls = (ctx.vecWriter.asInstanceOf[MemWriter[String]]).result()
//      val totalNodes =
//      println("Method count: " + ls.length)
//      println("Node kind: " + ctx.indexer.maxIndex + 1)
//      println("Total node count: " + ls.foldLeft(0){(count, vec)=>
//        count + vec.count
//      })
//
//      //---------------------
//      val sampleVec = ctx.vecWriter.asInstanceOf[MemWriter[String]](1).asInstanceOf[ArrayVec[String]]
//      println(sampleVec.methodName + "   " + sampleVec.intern.count(_=>true))
//      println(
//        sampleVec.intern.foldLeft(0){(sum: Int, i)=>sum + i}
//      )
//    }
//  }
//}
