package codesniffer.deckard.vgen

import java.io.{FilenameFilter, FileInputStream, File}

import codesniffer.deckard.Location
import codesniffer.java8.{CompilationUnitListener, Java8Parser, Java8Lexer}
import org.antlr.v4.runtime.atn.PredictionMode
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.antlr.v4.runtime.{CommonTokenStream, ANTLRInputStream}

/**
 * Created by Bowen Cai on 5/22/2015.
 */
class SlicerScanner[F](val context: Context[F]) {

  val _vgen = new SkipLocksVecGen[F]
  //TopDownVecGen[F] //SkipLockMethodVisitor //
  val _ct = new SkipLocksSubTreeCounter[F]
  val methodVisitor = new SlicerVecGen[F](_vgen, _ct)

  val classVisitor = new ClassVisitor[F]
  val fileVisitor = new FileVisitor[F]

  fileVisitor.setClassVisitor(classVisitor)

  // assemble visitors
  classVisitor.setMethodVisitor(methodVisitor)

  _vgen.setClassVisitor(classVisitor)
  _ct.setClassVisitor(classVisitor)


  def scanFile(src: File): Unit = {
    require(src.isFile)
    if (!context.config.filterFile(src)) {
      // update location
      val fileName = src.getName //.substring(path.length - 1)
      context.currentLocation = new Location(fileName, 0, 0, null)

      val stream = new FileInputStream(src)
      val cu = try {
        //        JavaParser.setDoNotConsiderAnnotationsAsNodeStartForCodeAttribution(true)
        //        JavaParser.parse(stream, "UTF-8", false)
        val lex = new Java8Lexer(new ANTLRInputStream(stream))
        val tokens = new CommonTokenStream(lex)

        //printTokens(lex);
        val parser = new Java8Parser(tokens)
        parser.getInterpreter.setPredictionMode(PredictionMode.SLL)
        val tree = parser.compilationUnit
        val walker = new ParseTreeWalker

        // Fills out the compilationUnit object
        val listener: CompilationUnitListener = new CompilationUnitListener(tokens)
        walker.walk(listener, tree)
        listener.getCompilationUnit

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