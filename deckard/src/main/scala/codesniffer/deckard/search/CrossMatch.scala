package codesniffer.deckard.search

import java.io.File
import java.util

import codesniffer.api.Node
import codesniffer.api.body.MethodDeclaration
import codesniffer.api.expr.ThisExpr
import codesniffer.api.stmt.EmptyStmt
import codesniffer.deckard.{WeightedVec, CharacVec, Indexer, MemWriter}
import codesniffer.deckard.vgen._


import scala.collection.convert.wrapAsScala._
import scala.concurrent._
import scala.util.{Failure, Success}

/**
* Created by Bowen Cai on 5/15/2015.
*/
object CrossMatch {

  type SortedList = util.TreeMap[Double, (CharacVec[String], CharacVec[String])]

  def vgen(path: String, idx: Indexer[String], cfg: DirScanConfig): MemWriter[String] = {
    val dir = new File(path)
    require(dir.exists() && dir.canRead)
    val vs = new MemWriter[String]
    vs.sizeHint(1024)
    val scanner = new SrcScanner(new Context[String](cfg, null, null, idx, vs))
    // save exact source to vector, for manually check
//    scanner.methodVisitor.before = (m: MethodDeclaration, ctx: Context[String]) => new WeightedVec(BasicVecGen.newVec(m, ctx))
    val mv = new SkipLocksVecGen[String]
    scanner.methodVisitor = mv;
    mv.classVisitor = scanner.classVisitor
    scanner.classVisitor.setMethodVisitor(mv)
    mv.after =
      (m: MethodDeclaration, v: CharacVec[String], ctx: Context[String]) => {
        if (v.count > 20) {
          v.data = Some(m.toString.intern())
          ctx.vecWriter.write(v)
        }
      }

    dir match {
      case where if where.isDirectory => scanner.scanDir(where, recursive = true)
      case src if src.isFile => scanner.processFile(src)
    }
    System.gc()
    vs
  }

  def findMatch(vLib: CharacVec[String], vApps: MemWriter[String], result: SortedList): SortedList = {
    val c1 = vLib.count
    val threshold = 18
    for (vApp <- vApps) {
      val c2 = vApp.count
      if (math.abs(c1 - c2) < 60) {
        val dist = vLib.distance(vApp)
        if (dist < threshold)
          result.put(dist, (vLib, vApp.asInstanceOf[CharacVec[String]]))
      }
    }
    result
  }

  def main(args: Array[String]): Unit = {
    var path2lib: String = "E:\\research\\top\\guava\\guava\\src"
//    val path2lib = "E:\\research\\top\\guava\\guava\\src\\com\\google\\common\\collect"
//    var path2lib: String = "E:\\research\\top\\jdk-1.7\\java\\util\\concurrent"
    var path2Apps = Array("E:\\\\research\\\\top\\\\h2-1.4.187-sources")//,
//    val path2Apps = Array("E:\\research\\top\\h2-1.4.187-sources\\org\\h2\\api")
//      "E:\\research\\top\\derby")//,
//      "E:\\research\\top\\Openfire",
//      "E:\\research\\top\\spring-framework",
//      "D:\\Program Files\\adt-bundle-windows-x86_64-20130219\\sdk\\sources\\android-19")

    var resultSize = 20

//    if (args != null && args.length > 2) {
//      resultSize = Integer.parseInt(args(0))
//      path2lib = args(1)
//      path2Apps = args.drop(2)
//    } else {
//      println("Usage: <result_size> <path-to-library> <path-to-app> <path-to-app> ...")
//      System.exit(1)
//    }

    println(s"Matching library $path2lib against applications:")
    path2Apps.foreach(println)

    /**
     **************************************************************************
      *  prepare
      *
     */
    val appCount = path2Apps.length
    val procCount = Runtime.getRuntime.availableProcessors()
    implicit val _exe = ExecutionContext.fromExecutor(java.util.concurrent.Executors.newFixedThreadPool(procCount)).prepare()

    val _nodeFilter = (node: Node)=>node.isInstanceOf[EmptyStmt] || node.isInstanceOf[ThisExpr]
    val fileNameFilter = (name: String) => (
        name.equals("package-info.java") // filter out package file
        || name.endsWith("Tests.java") // filter out test file
        || name.endsWith("Test.java") // filter out test file
      )

    val _appConfig = new DirScanConfig
    _appConfig.filterDirName = fileNameFilter
    _appConfig.filterNode = _nodeFilter

    val _libConfig = new DirScanConfig
    _libConfig.filterDirName = fileNameFilter
    _libConfig.filterNode = _nodeFilter
//    in a library, only public method is open to use
//    _libConfig.filterMethod = (m: MethodDeclaration) => !Modifier.isPublic(m.getModifiers)

    // common indexer, ensure vectors from different projects are generated in same coordinate
    val _indexer = new Indexer[String]

    /**
     **************************************************************************
     *  config & generate vectors
     *
     */
    var t0 = System.currentTimeMillis()
    val vsLib = vgen(path2lib, _indexer, _libConfig)
    var t1 = System.currentTimeMillis()
    println(s"library searched, ${vsLib.size} vectors generated, time ${t1 - t0} ms")

    val vsApps = new Array[MemWriter[String]](appCount)
    val results = (0 until appCount).map(_=>new SortedList).toArray
    t0 = System.currentTimeMillis()
    val tasks = for (i <- 0 until appCount) yield future[Unit] {
      for (vLib <- vsLib) {
        val _a = vsApps(i)
        val vApp = if (_a != null) _a
        else {
          val _vs = vgen(path2Apps(i), _indexer, _appConfig)
          vsApps(i) = _vs
          _vs
        }
        findMatch(vLib.asInstanceOf[CharacVec[String]], vApp, results(i))
      }
      while (results(i).size() * 2 > resultSize * 3) results(i).pollLastEntry()
    }

    Future.sequence(tasks) onComplete {
      case Success(r) =>
        t1 = System.currentTimeMillis()
        println(s"find ${results.foldLeft(0) { (s, e) => s + e.size() }} clone pair, time ${t1 - t0} ms")
        var i = 0
        while (i < path2Apps.length) {
          val result = results(i)
          println(s"Result for application ${path2Apps(i)}")
          var rank = 1
          for ((dist, pair) <- result) {
            println(s"Rank $rank, distance $dist\r\nnode count: ${pair._1.count}, ${pair._1.location}\r\n${pair._1.data.get}")
            println(s"node count: ${pair._2.count}, ${pair._2.location}\r\n${pair._2.data.get}")
            println()
            rank += 1
          }
          println("\r\n")
          i += 1
        }
        System.exit(0)
      case Failure(t) =>
        println(s"Search failed, error:$t")
    }


  }

}
