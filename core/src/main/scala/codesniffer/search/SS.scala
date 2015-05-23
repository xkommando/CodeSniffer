package codesniffer.search

import java.io.File
import java.lang.reflect.Modifier
import java.util

import codesniffer.core.{ArrayVec, CharacVec, MemWriter, Indexer}
import codesniffer.vgen.{Context, SlicerScanner, Config}
import com.github.javaparser.ast.Node
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.expr.ThisExpr
import com.github.javaparser.ast.stmt.EmptyStmt

import scala.collection.convert.wrapAsScala._
import scala.collection.mutable
import scala.concurrent._
import scala.util.{Failure, Success}

/**
 * Created by Bowen Cai on 5/22/2015.
 */
object SS {

  type SortedList = util.TreeMap[Double, (CharacVec[String], CharacVec[String])]


  def main (args: Array[String]): Unit = {

//    var path2lib: String = "E:\\research\\top\\guava\\guava\\src"
//    var path2lib = "E:\\research\\top\\guava\\guava\\src\\com\\google\\common\\util\\concurrent"
    var path2lib = "E:\\research\\top\\jdk-1.7\\java\\util\\concurrent"
//    var path2App: String = "E:\\research\\top\\derby\\java"
    var path2App = "E:\\research\\top\\h2-1.4.187-sources"
    var resultSizePerFunc = 8
    var resultSizeTotal = 200

    val _nodeFilter = (node: Node)=>node.isInstanceOf[EmptyStmt] || node.isInstanceOf[ThisExpr]
    val _libConfig = new Config
    _libConfig.filterDirName = (name: String) => (
      name.equals("package-info.java") // filter out package file
        //        || name.endsWith("Tests.java") // filter out test file
        || name.endsWith("Test.java") // filter out test file
      )
    _libConfig.filterNode = _nodeFilter
    _libConfig.filterMethod = (m: MethodDeclaration) => !Modifier.isPublic(m.getModifiers)
    val _appCfg = new Config
//    _appCfg.filterDirName = (name: String) => (
//      name.equals("package-info.java") // filter out package file
//        || name.endsWith("Test.java") // filter out test file
//      )
    _appCfg.filterNode = _nodeFilter

    val _indexer = new Indexer[String]

    val vsLib = CrossMatch.vgen(path2lib, _indexer, _libConfig)
    println(s"Library $path2lib processed, ${vsLib.size} generated")

//    val searchedApp = new mutable.HashMap[(Int, Int), MemWriter[String]]
//    searchedApp.sizeHint(9999)

    val appDir = new File(path2App)

    val summary = new SortedList

    val procNum = Runtime.getRuntime.availableProcessors() * 3 / 2
    implicit val _exe = ExecutionContext.fromExecutor(java.util.concurrent.Executors.newFixedThreadPool(procNum)).prepare()
    println(s"searching applications with $procNum threads")
    val length = vsLib.length
    val step = length / procNum

    val t0 = System.currentTimeMillis()
    val tasks = for (left <- Range(0, length, step)) yield future[Unit] {
      val right = if (length - left < step) length else left + step
      for (i <- left until right) {
        val libFunc = vsLib(i)
        val libC = libFunc.count
        val lower = libC * 8 / 10
        val upper = libC * 4 / 3

        val appVecList = {
          val ls = new MemWriter[String]
          ls.sizeHint(1024)
          val scanner = new SlicerScanner[String](new Context(_appCfg, null, null, _indexer, ls))
          scanner.methodVisitor.lowerBound = lower
          scanner.methodVisitor.upperBound = upper
          appDir match {
            case where if where.isDirectory => scanner.scanDir(where, recursive = true)
            case src if src.isFile => scanner.scanFile(src)
          }
          ls
        }
        /// WARN: racing!!!
//        val appVecList = searchedApp.getOrElse((lower, upper),{
//          val ls = new MemWriter[String]
//          ls.sizeHint(9999)
//          val scanner = new SlicerScanner[String](new Context(_appCfg, null, null, _indexer, ls))
//          scanner.methodVisitor.vecGen.after =
//            (m: MethodDeclaration, v: CharacVec[String], ctx: Context[String]) => {
//              if (v.count > 20) {
////                v.data = Some(m.toString.intern())
//                ctx.vecWriter.write(v)
//              }
//            }
//          scanner.methodVisitor.lowerBound = lower
//          scanner.methodVisitor.upperBound = upper
//          appDir match {
//            case where if where.isDirectory => scanner.scanDir(where, recursive = true)
//            case src if src.isFile => scanner.scanFile(src)
//          }
//          searchedApp.put((lower, upper), ls)
//          ls
//        })
        if (appVecList.size > 0) {
          val result = new SortedList
            for (appFunc <- appVecList) {
              val appC = appFunc.count
              if (math.abs(appC - libC) < 40) {
                val dist = libFunc.asInstanceOf[ArrayVec[String]].math.EuclideanDist(appFunc.asInstanceOf[ArrayVec[String]])
                if (dist < 20)
                  result.put(dist, (libFunc, appFunc)) // .asInstanceOf[CharacVec[String]]))
              }
            }
          while (result.size() > resultSizePerFunc) result.pollLastEntry()
          println(s"app searched for ${libFunc.location.scope}")
          summary.putAll(result)
        }
        System.gc()
      } // for apps
      System.gc()
      System.gc()
      println(s"lib $left to $right searched\r\n")
    }

    Future.sequence(tasks) onComplete{
      case Success(r) =>
        while (summary.size() > resultSizeTotal) summary.pollLastEntry()
        val t1 = System.currentTimeMillis()

//        searchedApp.clear()
//        searchedApp = null

        var rank = 1
        println(s"find ${summary.size()} clone pair, time ${t1 - t0} ms")

        for ((dist, pair) <- summary) {
          println(s"Rank $rank, distance $dist\r\nnode count: ${pair._1.count}, ${pair._1.location}\r\n${pair._1.data.get}")
          println(s"node count: ${pair._2.count}, ${pair._2.location}\r\n${pair._2.data.get}")
          println()
          rank += 1
        }
        System.exit(0)
      case Failure(t) =>
        println(s"Search failed")
        t.printStackTrace()
        System.exit(0)
    }



  }
}
