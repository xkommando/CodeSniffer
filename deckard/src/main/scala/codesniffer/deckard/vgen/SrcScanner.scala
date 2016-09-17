package codesniffer.deckard.vgen

import java.io.{File, FileInputStream, FilenameFilter}

import codesniffer.api.CompilationUnit
import codesniffer.api.body.MethodDeclaration
import codesniffer.api.visitor.VoidVisitorAdapter
import codesniffer.deckard.Location
import codesniffer.java8.{CompilationUnitListener, Java8Lexer, Java8Parser}
import org.antlr.v4.runtime
import org.antlr.v4.runtime.atn.PredictionMode
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}

/**
 * Created by Bowen Cai on 5/1/2015.
 */
class SrcScanner[F](val context: Context[F]) {


  val classVisitor = new ClassVisitor[F]
  val fileVisitor = new FileVisitor[F]
  fileVisitor.setClassVisitor(classVisitor)

  var methodVisitor: VoidVisitorAdapter[Context[F]] = null

  def methodVisitor_(_methodVisitor: VoidVisitorAdapter[Context[F]]): Unit = {
    this.methodVisitor = _methodVisitor
    this.classVisitor.setMethodVisitor(this.methodVisitor)
  }

  var processFile: (File)=> Unit = (srcFile)=>{
    require(srcFile.isFile)
    if (!context.config.filterFile(srcFile)) {
      // update location
      val fileName = srcFile.getPath //.substring(path.length - 1)
      context.currentLocation = new Location(fileName, 0, 0, null)

      val stream = new FileInputStream(srcFile)

//      compilationUnit =
      val cu = try {
//        JavaParser.setDoNotConsiderAnnotationsAsNodeStartForCodeAttribution(true)
//        JavaParser.parse(stream, "UTF-8", false)

        val antlrStream = new runtime.ANTLRInputStream(stream)
        val lex = new Java8Lexer(antlrStream)
        val tokens = new CommonTokenStream(lex)

        //printTokens(lex);
        val parser = new Java8Parser(tokens)

        parser.getInterpreter.setPredictionMode(PredictionMode.SLL)
        val tree = parser.compilationUnit
        val walker = new ParseTreeWalker

        // Fills out the compilationUnit object
        val listener = new CompilationUnitListener(tokens)
        walker.walk(listener, tree)
        listener.getCompilationUnit

      } catch {
        case e: Exception =>
          throw new RuntimeException(s"Could not parse file ${srcFile.getPath}", e)
      }

      try {
        fileVisitor.visit(cu, context)
      } catch {
        case e: Exception =>
          throw new RuntimeException(s"Could not travel though unit ${srcFile.getPath}", e)
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
          processFile(src)
      //      case _ => throw new RuntimeException(s"UNK file, $sub in $dir")
    }
  }

}
