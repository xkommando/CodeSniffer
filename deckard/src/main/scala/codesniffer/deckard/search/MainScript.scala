package codesniffer.deckard.search

import java.io.File
import java.util
import java.util.concurrent.atomic.AtomicInteger

import codesniffer.core._
import codesniffer.deckard.{ArrayVec, Indexer, MemWriter}
import codesniffer.deckard.vgen.{Context, SrcScanner, DirScanConfig}
import codesniffer.deckard.vgen._
import codesniffer.api.Node
import codesniffer.api.expr.ThisExpr
import codesniffer.api.stmt.EmptyStmt

import scala.collection.convert.wrapAsScala._
import scala.concurrent.{ExecutionContext, Future, future}
import scala.util.{Failure, Success}

/**
 *
 *
 * Created by Bowen Cai on 4/25/2015.
 */
object MainScript {

  // to a directory, or a single source file
//    val path = "D:\\__jvm\\spring-framework-master"
//  val path = "D:\\repos\\cache\\ehcache-2.7.5"
  val path = "C:\\Users\\bowen\\Downloads\\tmp"
//  val path = "D:\\__TEMP__\\src\\Src3.java"
  var resultSize = 16
//    val path = "D:\\__jvm\\spring-framework-master\\spring-framework-master\\spring-jdbc"

  def main(args: Array[String]): Unit = {

//    var resultSize = -1
//    var path: String = null
//    if (args != null && args.length == 2) {
//      path = args(0)
//      resultSize = args(1).toInt
//      println(s"Scanning directory $path, estimated result size $resultSize")
//    } else {
//      println("Usage: <path to source directory> <expected result size>")
//      sys.exit(1)
//    }
    /** **************************************************************************
      *  prepare
      */
    val dir = new File(path)
    require(dir.exists() && dir.canRead)
    // filter out:
    // package file
    // test classes
    // meaningless statements
    val config = new DirScanConfig
    config.filterDirName = (name: String) => (
      name.equals("package-info.java") // filter out package file
        || name.endsWith("Tests.java") // filter out test file
      )
    config.filterNode = (node: Node) => node.isInstanceOf[EmptyStmt] || node.isInstanceOf[ThisExpr]
    val vecCollector = new MemWriter[String]
    val scanner = new SrcScanner(new Context(config, currentLocation = null, data = null, new Indexer[String], vecCollector))
    val mv = new BasicVecGen[String]//SkipLocksVecGen[String]
    scanner.methodVisitor = mv
    mv.classVisitor = scanner.classVisitor
    scanner.classVisitor.setMethodVisitor(mv)
    /** **************************************************************************
      *  generate vectors
      */
    val tv1 = System.currentTimeMillis()
    dir match {
      case where if where.isDirectory => scanner.scanDir(where, recursive = true)
      case src if src.isFile => scanner.processFile(src)
    }
    val tv2 = System.currentTimeMillis()
    /** **************************************************************************
      * search for similar methods
      */
    val vecCount = vecCollector.length
    println(s"$vecCount vectors generated, time ${tv2 - tv1} ms")

    type SortedList = util.TreeMap[Double, (ArrayVec[_], ArrayVec[_])]

    val procCount: Int = 1//Runtime.getRuntime.availableProcessors()

    // forkjoin out performs threadpool
    //    import ExecutionContext.Implicits.global
    // old threadpool
    implicit val exe = ExecutionContext.fromExecutor(java.util.concurrent.Executors.newFixedThreadPool(procCount)).prepare()

    val partResultSize = vecCount / procCount // expected result size of each worker
    println(s"Searching for code pairs with $procCount threads")

    val t1 = System.currentTimeMillis()

    val threshold = 9999//config.distThreshold

    val right = new AtomicInteger(vecCount)
    val dprocCount = procCount.toDouble
    val tasks = for (i <- 1 to procCount) yield future[SortedList] {
      val sortedList = new SortedList()
      // assign task to each worker with average workload
      val left = vecCount - (math.sqrt(i / dprocCount) * vecCount).toInt
      val _r = right.get()
      for (j <- left until _r) {
        for (k <- (i + 1) until vecCount) {
          val a = vecCollector(j).asInstanceOf[ArrayVec[String]]
          val ac = a.count
          val b = vecCollector(k).asInstanceOf[ArrayVec[String]]
          val bc = b.count
//          if (ac > 20 && bc > 20 && math.abs(ac - bc) < 60) { // this could significantly reduce comparison
            val dist = a.distance(b)
//            val dist = ArrayVec.math.EuclideanDist(b)
            if (dist < threshold)
              sortedList.put(dist, (a, b))
//            val dist = a.math.CosineDist(b)
//            if (dist < 0.01)
//              sortedList.put(dist, (a, b))
//          }
        }
        // chop off redundant data
        while (sortedList.size() > partResultSize) sortedList.pollLastEntry()
      }
      println(s"vectors from $left to $right searched")
      right.set(left)
      sortedList
    }
    //  val results = Await.result(Future.sequence(tasks), 10.minutes)
    Future.sequence(tasks) onComplete {
      case Success(results) =>
        val result = results.foldLeft(new SortedList) { (rs, e) =>
          rs.putAll(e)
          rs
        }
        while (result.size() > resultSize * 1.2) result.pollLastEntry()
        if (result.firstKey() < 0.001) result.pollFirstEntry() // pop a singlar false positive

        val t2 = System.currentTimeMillis()
        /** **************************************************************************
          * report
          */
        println(s"find ${result.size()} clone pair, time ${t2 - t1} ms")
        var i = 0
        for ((dist, pair) <- result) {
          println(s"Rank $i distance $dist :(node count: ${pair._1.count}, node count ${pair._2.count})\r\n${pair._1.location}\r\n${pair._2.location}\r\n")
          i += 1
        }
      case Failure(t) =>
        println(s"Search failed, error:$t")
    }

    //    single thread version

    //  val result = new SortedList()
    //
    //  val t3 = System.currentTimeMillis()
    //  for (i <- 0 until vecCount) {
    //    for (j <- i + 1 until vecCount) {
    //      val a = vecCollector(i).asInstanceOf[CharacVec[String]]
    //      val b = vecCollector(j).asInstanceOf[CharacVec[String]]
    //      if (math.abs(a.count - b.count) < 60) {
    //        val dist = a.math.EuclideanDist(b)
    //        if (dist < 15)
    //          result.put(dist, (a, b))
    //      }
    //    }
    //    // chop redundant data
    //    while (result.size() > partResultSize) result.pollLastEntry()
    //  }
    //  val t4 = System.currentTimeMillis()
  }
}

