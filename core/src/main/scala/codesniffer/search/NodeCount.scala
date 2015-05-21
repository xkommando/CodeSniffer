package codesniffer.search

import java.io.File
import java.lang.reflect.Modifier
import java.util
import java.util.concurrent.atomic.{AtomicInteger, AtomicLong, AtomicReference}

import codesniffer.core._
import codesniffer.vgen.{Config, Context, SrcScanner}
import com.github.javaparser.ast.Node
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.expr.ThisExpr
import com.github.javaparser.ast.stmt.EmptyStmt

import scala.collection.convert.wrapAsScala._


/**
 * Created by Bowen Cai on 5/1/2015.
 */
object NodeCount {

  val methodCount = new AtomicLong(0L)

  // top level statement
  val topStmtCount = new AtomicLong(0L)
  val stmtGrp = new util.TreeMap[Int, Int]()
  val nodeCount = new AtomicLong(0L)
  val nodeGrp = new util.TreeMap[Int, Int]()

  val maxNodeNumber = new AtomicInteger(0)
  val maxNodeLoc = new AtomicReference[Location](null)

  def main(args: Array[String]): Unit = {

//    var path = "D:\\__TEMP__\\src\\Src1.java"
//    var path: String = "D:\\Program Files\\adt-bundle-windows-x86_64-20130219\\sdk\\sources\\android-19"
    var path: String = "E:\\research\\top\\spring-framework"
    //    var path: String = _
//    if (args != null && args.length == 1) {
//      path = args(0)
//      println(s"Scanning directory $path")
//    } else {
//      println("Usage: <path to source directory>")
//      sys.exit(1)
//    }

    val dir = new File(path)
    require(dir.exists() && dir.canRead)
    val config = new Config
    config.filterDirName = (name: String) => (
      name.equals("package-info.java") // filter out package file
        || name.endsWith("Tests.java") // filter out test file
      )
    config.filterNode = (node: Node) => node.isInstanceOf[EmptyStmt] || node.isInstanceOf[ThisExpr]
//    config.filterMethod = (m: MethodDeclaration) => !Modifier.isPublic(m.getModifiers)

    val vecCollector = new MemWriter[String]
    val scanner = new SrcScanner(new Context(config, null, new Indexer[String], vecCollector))

    scanner.methodVisitor.before = (method: MethodDeclaration, c: Context[String])=> {
      methodCount.incrementAndGet()
      val _stmtNum = method.getBody.getStmts.size()
      topStmtCount.addAndGet(_stmtNum)
      stmtGrp.synchronized {
        val old = stmtGrp.getOrDefault(_stmtNum, 0)
        stmtGrp.update(_stmtNum, old + 1)
      }
      new CounterVec[String](c.currentLocation)
    }

    scanner.methodVisitor.after = (method: MethodDeclaration, last: CharacVec[String], c: Context[String])=> {
      val c = last.count
      nodeCount.addAndGet(c)
      nodeGrp.synchronized{
        val old = nodeGrp.getOrDefault(c, 0)
        nodeGrp.update(c, old + 1)
      }
      if (c > maxNodeNumber.get()) {
        maxNodeNumber.set(c)
        maxNodeLoc.set(last.location)
      }
      vecCollector.clear()
      last
    }

    dir match {
      case where if where.isDirectory => scanner.scanDir(where, recursive = true)
      case src if src.isFile => scanner.scanFile(src)
    }
    
    println(s"method count: $methodCount, top level stmt count: $topStmtCount")
    println(s"stmt per method: ${topStmtCount.doubleValue() / methodCount.doubleValue()}")

    println("stmt count:")
    stmtGrp.foreach(println)

    println("nodeCount: " + methodCount)
    println(s"node per method: ${nodeCount.doubleValue() / methodCount.doubleValue()}")

    println("node count:")
    nodeGrp.foreach(println)

    println(s"max node ${maxNodeNumber.get()}, appeared at $maxNodeLoc")
    println(scanner.context.indexer)
  }
}