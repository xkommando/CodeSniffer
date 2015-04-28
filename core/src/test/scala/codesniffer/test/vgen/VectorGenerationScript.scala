package codesniffer.test.vgen

import java.io.{FileInputStream, FilenameFilter, File}
import codesniffer.core._
import codesniffer.vgen._
import com.github.javaparser.JavaParser
import com.github.javaparser.ast.Node
import com.github.javaparser.ast.expr.ThisExpr
import com.github.javaparser.ast.stmt.EmptyStmt

import scala.collection.convert.wrapAsScala._
import scala.reflect.ClassTag

/**
 * Created by Bowen Cai on 4/25/2015.
 */
object VectorGenerationScript extends App {

  //---------------------------------------------------------------------------
  //    config

  // to a directory, or a single source file
  val path: String = "D:\\__jvm\\cache"


  /****************************************************************************
   *          initialize
   */

  val mv = new MethodVisitor
  val cv = new ClassVisitor
  val fv = new FileVisitor

  // assemble visitors
  cv.setMethodVisitor(mv)
  mv.setClassVisitor(cv)
  fv.setClassVisitor(cv)

  // generated vectors will be collected here
  Console.setOut(System.err)
  val vecCollector = new MemWriter {
    override def write[T: ClassTag] (vec: CharacVec[T]): Unit = {
      super.+=(vec)
      println("Visited: " + vec.location)
    }
  }

  val ctx = new Context(new Config, null, new Indexer[String], vecCollector)
  // filter out meaningless statements
  ctx.config.NodeFilter = (node: Node)=> node.isInstanceOf[EmptyStmt] || node.isInstanceOf[ThisExpr]

  val handleFile = (src: File)=> {
    require(src.isFile)

    // update location
    val fileName = src.getPath
    val prevLoc = ctx.currentLocation
    ctx.currentLocation = new Location(fileName, 0, null)

    val stream = new FileInputStream(src)
    val cu = JavaParser.parse(stream, "UTF-8", false)

    // search for class definition
    fv.visit(cu, ctx)
    stream.close()
    ctx.currentLocation = prevLoc
  }

  def handleDir (dir: File): Unit = {
    require(dir.isDirectory)
    
    for (sub <- dir.listFiles()) sub match {
      case subDir if subDir.isDirectory => handleDir(subDir)
      case src if src.isFile =>
        if (src.getPath.endsWith(".java"))
          handleFile(src)
//      case _ => throw new RuntimeException(s"UNK file, $sub in $dir")
    }
  }

  /****************************************************************************
    *          kick off
    */

  val dir = new File(path)
  require(dir.exists() && dir.canRead)
  dir match {
    case subDir if subDir.isDirectory => handleDir(subDir)
    case src if src.isFile => handleFile(src)
  }

  /****************************************************************************
    *          report
    */
  println(vecCollector.length + " Vectors generated")

}
