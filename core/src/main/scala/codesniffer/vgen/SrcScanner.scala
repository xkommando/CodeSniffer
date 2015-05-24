package codesniffer.vgen

import java.io.{File, FileInputStream, FilenameFilter}

import codesniffer.core.Location
import com.github.javaparser.{ASTHelper, JavaParser}

/**
 * Created by Bowen Cai on 5/1/2015.
 */
class SrcScanner[F](val context: Context[F]) {

  val methodVisitor = new SkipLocksVecGen[F]
  val classVisitor = new ClassVisitor[F]
  val fileVisitor = new FileVisitor[F]

  fileVisitor.setClassVisitor(classVisitor)
  // assemble visitors
  classVisitor.setMethodVisitor(methodVisitor)
  methodVisitor.setClassVisitor(classVisitor)


  def scanFile(src: File): Unit = {
    require(src.isFile)
    if (!context.config.filterFile(src)) {
      // update location
      val fileName = src.getName //.substring(path.length - 1)
      context.currentLocation = new Location(fileName, 0, 0, null)

      val stream = new FileInputStream(src)
      val cu = try {
        JavaParser.setDoNotConsiderAnnotationsAsNodeStartForCodeAttribution(true)
        JavaParser.parse(stream, "UTF-8", false)
      } catch {
        case e: Exception =>
          throw new RuntimeException(s"Could not parse file ${src.getPath}", e)
      }
      try {
        fileVisitor.visit(cu, context)
      } catch {
        case e: Exception =>
          throw new RuntimeException(s"Could not travel though unit ${src.getPath}", e)
      }
      stream.close()
    }
  }

  def scanDir(dir: File, recursive: Boolean): Unit = {
    require(dir.isDirectory)

    for (sub <- dir.listFiles(new FilenameFilter {
      override def accept(dir: File, name: String): Boolean = !context.config.filterDirName(name)
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
