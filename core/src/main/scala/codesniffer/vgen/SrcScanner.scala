package codesniffer.vgen

import java.io.{File, FileInputStream, FilenameFilter}

import codesniffer.core.{Indexer, Location, MemWriter}
import com.github.javaparser.JavaParser

/**
 * Created by Bowen Cai on 5/1/2015.
 */
class SrcScanner(val context: Context) {

  val methodVisitor = new MethodVisitor
  val classVisitor = new ClassVisitor
  val fileVisitor = new FileVisitor

  // assemble visitors
  classVisitor.setMethodVisitor(methodVisitor)
  methodVisitor.setClassVisitor(classVisitor)
  fileVisitor.setClassVisitor(classVisitor)

  def scanFile(src: File): Unit = {
    require(src.isFile)
    if (!context.config.filterFile(src)) {
      // update location
      val fileName = src.getName //.substring(path.length - 1)
      context.currentLocation = new Location(fileName, 0, null)

      val stream = new FileInputStream(src)
      val cu = try {
         JavaParser.parse(stream, "UTF-8", false)
      } catch {
        case e: Exception => println(s"Could not parse file ${src.getPath}")
          throw e
      }
      // search for class definition
      fileVisitor.visit(cu, context)
      stream.close()
    }
  }

  def scanDir(dir: File, recursive: Boolean): Unit = {
    require(dir.isDirectory)

    for (sub <- dir.listFiles(new FilenameFilter {
      override def accept(dir: File, name: String): Boolean = !context.config.filterFileName(name)
    })) sub match {
      case subDir if subDir.isDirectory =>
        if (recursive)
          scanDir(subDir, recursive)
      case src if src.isFile =>
        if (src.getName.endsWith(".java"))
          scanFile(src)
      //      case _ => throw new RuntimeException(s"UNK file, $sub in $dir")
    }
  }

}
