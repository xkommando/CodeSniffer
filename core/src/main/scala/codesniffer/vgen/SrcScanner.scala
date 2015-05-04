package codesniffer.vgen

import java.io.{File, FileInputStream, FilenameFilter}

import codesniffer.core.{Indexer, Location, MemWriter}
import com.github.javaparser.JavaParser

/**
 * Created by Bowen Cai on 5/1/2015.
 */
class SrcScanner(path: String, cfg: Config) {

  val methodVisitor = new MethodVisitor
  val classVisitor = new ClassVisitor
  val fileVisitor = new FileVisitor

  // assemble visitors
  classVisitor.setMethodVisitor(methodVisitor)
  methodVisitor.setClassVisitor(classVisitor)
  fileVisitor.setClassVisitor(classVisitor)

  // vectors generated will be collected to this one
  val vecCollector = new MemWriter

  val ctx = new Context(cfg, null, new Indexer[String], vecCollector)

  def scanFile(src: File): Unit = {
    require(src.isFile)
    if (!ctx.config.filterFile(src)) {
      // update location
      val fileName = src.getPath.substring(path.length - 1)
      ctx.currentLocation = new Location(fileName, 0, null)

      val stream = new FileInputStream(src)
      val cu = JavaParser.parse(stream, "UTF-8", false)

      // search for class definition
      fileVisitor.visit(cu, ctx)
      stream.close()
    }
  }

  def scanDir(dir: File, recursive: Boolean): Unit = {
    require(dir.isDirectory)

    for (sub <- dir.listFiles(new FilenameFilter {
      override def accept(dir: File, name: String): Boolean = !ctx.config.filterFileName(name)
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
