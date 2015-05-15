package codesniffer.search

import java.io.File
import java.lang.reflect.Modifier
import java.util

import codesniffer.core.{CharacVec, Indexer, MemWriter}
import codesniffer.vgen.{Config, Context, SrcScanner}
import com.github.javaparser.ast.Node
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.expr.ThisExpr
import com.github.javaparser.ast.stmt.{EmptyStmt, SynchronizedStmt}

import scala.collection.convert.wrapAsScala._

/**
 * Created by Bowen Cai on 5/15/2015.
 */
object CrossMatch {

  type SortedList = util.TreeMap[Double, (CharacVec[String], CharacVec[String])]

  def checkDir(path: String) : Unit = {
    val dir = new File(path)
    require(dir.exists() && dir.canRead)
  }

  def main(args: Array[String]): Unit = {

    var path2app: String = "E:\\research\\top\\spring-framework"
//    val path2app = "E:\\research\\top\\h2-1.4.187-sources\\org"
    var path2lib: String = "E:\\research\\top\\guava\\guava\\src\\com\\google"
    var resultSize = -1
    if (args != null && args.length == 3) {
      path2app = args(0)
      path2lib = args(1)
      resultSize = Integer.parseInt(args(2))
    } else {
      println("Usage: <path1> <path2> <result_size>")
      System.exit(1)
    }

    println(s"searching $path2app as the application directory\r\n$path2lib as the library directory")

    /**
     **************************************************************************
      *  prepare
      *
     */
    checkDir(path2app)
    checkDir(path2lib)

    val srcNameFilter = (name: String) => (
      name.equals("package-info.java") // filter out package file
        || name.endsWith("Tests.java") // filter out test file
      )
    val nodeFilter = (node: Node)=>node.isInstanceOf[EmptyStmt] || node.isInstanceOf[ThisExpr] || node.isInstanceOf[SynchronizedStmt]

    val appConfig = new Config
    appConfig.filterFileName = srcNameFilter
    appConfig.filterNode = nodeFilter

    val libConfig = new Config
    libConfig.filterFileName = srcNameFilter
    libConfig.filterNode = nodeFilter
    // only public method is open to use
    libConfig.filterMethod = (m: MethodDeclaration) => !Modifier.isPublic(m.getModifiers)

    // common indexer, ensure vectors from different libs are generated in same coordinate
    val indexer = new Indexer[String]
    // save exact source to vector, for manually check
    val saveSrc = (m: MethodDeclaration, v: CharacVec[_], ctx: Context)=>{
      v.data = Some(m.toString)
    }
    /**
     **************************************************************************
     *  config & generate vectors
     *
     */
    val vs1 = new MemWriter
    val scanner1 = new SrcScanner(new Context(appConfig, null, indexer, vs1))
    scanner1.methodVisitor.before = saveSrc
    var t0 = System.currentTimeMillis()
    new File(path2app) match {
      case where if where.isDirectory => scanner1.scanDir(where, recursive = true)
      case src if src.isFile => scanner1.scanFile(src)
    }
    var t1 = System.currentTimeMillis()
    println(s"application searched, ${vs1.size} vectors generated, time ${t1 - t0} ms")

    val vs2 = new MemWriter
    val scanner2 = new SrcScanner(new Context(libConfig, null, indexer, vs2))
    scanner2.methodVisitor.before = saveSrc
    t0 = System.currentTimeMillis()
    new File(path2lib) match {
      case where if where.isDirectory => scanner2.scanDir(where, recursive = true)
      case src if src.isFile => scanner2.scanFile(src)
    }
    t1 = System.currentTimeMillis()
    println(s"library searched, ${vs2.size} vectors generated, time ${t1 - t0} ms")

    val result = new SortedList()
    t0 = System.currentTimeMillis()
    for (v1 <- vs1; v2 <- vs2) {
      val c1 = v1.count
      val c2 = v2.count

      if (c1 > 20 && c2 > 20 && math.abs(c1 - c2) < 60) {
        // this could significantly reduce comparison
        //            val dist = a.dist2(b)
        val dist = v1.dist2(v2)
        if (dist < 20)
          result.put(dist, (v1.asInstanceOf[CharacVec[String]], v2.asInstanceOf[CharacVec[String]]))

        while (result.size() * 3 > resultSize * 2) result.pollLastEntry()
      }
    }
    t1 = System.currentTimeMillis()

    println(s"find ${result.size()} clone pair, time ${t1 - t0} ms")
    var i = 1
    for ((dist, pair) <- result) {
      println(s"Rank $i, distance $dist\r\nnode count: ${pair._1.count}, ${pair._1.location}\r\n${pair._1.data.get}")
      println(s"node count: ${pair._2.count}, ${pair._2.location}\r\n${pair._2.data.get}")
      println()
      i += 1
    }

  }
}
