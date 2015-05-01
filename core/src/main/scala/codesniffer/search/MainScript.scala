package codesniffer.search

import java.io.{File, FileInputStream, FilenameFilter}
import java.util
import java.util.concurrent.TimeUnit

import codesniffer.core._
import codesniffer.vgen._
import com.github.javaparser.JavaParser
import com.github.javaparser.ast.Node
import com.github.javaparser.ast.expr.ThisExpr
import com.github.javaparser.ast.stmt.EmptyStmt

import scala.collection.convert.wrapAsScala._
import scala.concurrent.{ExecutionContext, Future, future}
import scala.util.{Failure, Success}

/**
 * Created by Bowen Cai on 4/25/2015.
 */
object MainScript {

  def main(args: Array[String]): Unit = {
    //---------------------------------------------------------------------------
    //    config

    var resultSize = -1
    var path: String = null
    if (args != null && args.length == 2) {
      path = args(0)
      resultSize = args(1).toInt
      println(s"Scanning directory $path, estimated result size $resultSize")
    } else {
      println("Usage: <path to source directory> <expected result size>")
      sys.exit(1)
    }

    // to a directory, or a single source file
    //  val path = "D:\\__jvm\\spring-framework-master"
    //  val path = "D:\\__jvm\\cache\\ehcache-2.7.5"
    //  val path = "D:\\__jvm\\spring-framework-master\\spring-framework-master\\spring-jdbc"
    /** **************************************************************************
      * initialize
      */

    val mv = new MethodVisitor
    val cv = new ClassVisitor
    val fv = new FileVisitor

    // assemble visitors
    cv.setMethodVisitor(mv)
    mv.setClassVisitor(cv)
    fv.setClassVisitor(cv)

    // vectors generated will be collected to this one
    val vecCollector = new MemWriter
    //  {
    //    override def write[T: ClassTag] (vec: CharacVec[T]): Unit = {
    //      super.+=(vec)
    //      println("Visited: " + vec.location)
    //    }
    //  }

    val ctx = new Context(new Config, null, new Indexer[String], vecCollector)
    // filter out:
    // package file
    // test classes
    // meaningless statements
    ctx.config.filterFileName = (name: String) => (
      name.equals("package-info.java") // filter out package file
        || name.endsWith("Tests.java") // filter out test file
      )
    ctx.config.filterNode = (node: Node) => node.isInstanceOf[EmptyStmt] || node.isInstanceOf[ThisExpr]

    val handleFile = (src: File) => {
      require(src.isFile)
      if (!ctx.config.filterFile(src)) {
        // update location
        val fileName = src.getPath.substring(path.length - 1)
        ctx.currentLocation = new Location(fileName, 0, null)

        val stream = new FileInputStream(src)
        val cu = JavaParser.parse(stream, "UTF-8", false)

        // search for class definition
        fv.visit(cu, ctx)
        stream.close()
      }
    }

    def handleDir(dir: File): Unit = {
      require(dir.isDirectory)

      for (sub <- dir.listFiles(new FilenameFilter {
        override def accept(dir: File, name: String): Boolean = !ctx.config.filterFileName(name)
      })) sub match {
        case subDir if subDir.isDirectory => handleDir(subDir)
        case src if src.isFile =>
          if (src.getName.endsWith(".java"))
            handleFile(src)
        //      case _ => throw new RuntimeException(s"UNK file, $sub in $dir")
      }
    }

    /** **************************************************************************
      * kick off
      */

    val dir = new File(path)
    require(dir.exists() && dir.canRead)
    dir match {
      case subDir if subDir.isDirectory => handleDir(subDir)
      case src if src.isFile => handleFile(src)
    }

    /** **************************************************************************
      * search for similar methods
      */
    val vecCount = vecCollector.length
    println(vecCount + " Vectors generated")

    type SortedList = util.TreeMap[Double, (CharacVec[_], CharacVec[_])]

    val procCount = Runtime.getRuntime.availableProcessors()

    // forkjoin out performs threadpool
    import ExecutionContext.Implicits.global
    // old threadpool
//    implicit val exe = ExecutionContext.fromExecutor(java.util.concurrent.Executors.newFixedThreadPool(procCount)).prepare()

    val partResultSize = vecCount / 80 / procCount
    println(s"Searching for code pairs with $procCount threads")

    val t1 = System.currentTimeMillis()

    var right = vecCount
    val dprocCount = procCount.toDouble
    val tasks = for (i <- 1 to procCount) yield future[SortedList] {
      val sortedList = new SortedList()
      // assign task to each thread with average workload
      val left = vecCount - (math.sqrt(i / dprocCount) * vecCount).toInt
      for (j <- left until right) {
        for (k <- (i + 1) until vecCount) {
          val a = vecCollector(j).asInstanceOf[CharacVec[String]]
          val ac = a.count
          val b = vecCollector(k).asInstanceOf[CharacVec[String]]
          val bc = b.count
          if (ac > 20 && bc > 20 && math.abs(ac - bc) < 50) {
            val dist = a.math.EuclideanDist(b)
            if (dist < 15)
              sortedList.put(dist, (a, b))
          }
        }
        // chop redundant data
        while (sortedList.size() > partResultSize) sortedList.pollLastEntry()
      }
      println(s"vectors from $left until $right searched")
      right = left
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
        val t2 = System.currentTimeMillis()

        /** **************************************************************************
          * report
          */

        println(s"find ${result.size()} clone pair, time ${t2 - t1}")
        for ((dist, pair) <- result) {
          println(s" distance $dist :(node count: ${pair._1.count}, node count ${pair._2.count})\r\n${pair._1.location}\r\n${pair._2.location}")
        }
      case Failure(t) =>
        println(s"Search failed, error:$t")
    }

//    _es.awaitTermination(10, TimeUnit.MINUTES)
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
