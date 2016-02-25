//package codesniffer.codefunnel
//
//import java.io.{FilenameFilter, FileInputStream, File}
//
//import codesniffer.api.CompilationUnit
//import codesniffer.api.visitor.VoidVisitorAdapter
//import codesniffer.deckard.Location
//import codesniffer.deckard.vgen.{DirScanConfig, FileVisitor, ClassVisitor}
//import codesniffer.java8.{CompilationUnitListener, Java8Parser, Java8Lexer}
//import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}
//import org.antlr.v4.runtime.atn.PredictionMode
//import org.antlr.v4.runtime.tree.ParseTreeWalker
//
///**
//  * Created by Bowen Cai on 2/24/2016.
//  */
//class SrcDirScanner(config:DirScanConfig) {
//
//  var methodVisitor: VoidVisitorAdapter[Int]
//  var processCU: CompilationUnit=>Unit
//  val classVisitor = new ClassVisitor[Int]
//  val fileVisitor = new FileVisitor[Int]
//  fileVisitor.setClassVisitor(classVisitor)
//
//  def scanFile(src: File): Unit = {
//    require(src.isFile)
//    if (!config.filterFile(src)) {
//      // update location
//      val fileName = src.getName //.substring(path.length - 1)
//      val stream = new FileInputStream(src)
//      //      compilationUnit =
//      val cu = try {
//        //        JavaParser.setDoNotConsiderAnnotationsAsNodeStartForCodeAttribution(true)
//        //        JavaParser.parse(stream, "UTF-8", false)
//
//        val antlrStream = new ANTLRInputStream(stream)
//        val lex = new Java8Lexer(antlrStream)
//        val tokens = new CommonTokenStream(lex)
//
//        //printTokens(lex);
//        val parser = new Java8Parser(tokens)
//
//        parser.getInterpreter.setPredictionMode(PredictionMode.SLL)
//        val tree = parser.compilationUnit
//        val walker = new ParseTreeWalker
//
//        // Fills out the compilationUnit object
//        val listener = new CompilationUnitListener(tokens)
//        walker.walk(listener, tree)
//        listener.getCompilationUnit
//      } catch {
//        case e: Exception =>
//          throw new RuntimeException(s"Could not parse file ${src.getPath}", e)
//      } finally {
//        stream.close()
//      }
//    }
//  }
//
//  def scanDir(dir: File, recursive: Boolean): Unit = {
//    require(dir.isDirectory)
//    for (sub <- dir.listFiles(new FilenameFilter {
//      override def accept(dir: File, name: String): Boolean = !config.filterDirName(name)
//    })) sub match {
//      case subDir if subDir.isDirectory =>
//        if (recursive)
//          scanDir(subDir, recursive)
//      case src if src.isFile =>
//        if (src.getName.endsWith(".java"))
//          scanFile(src)
//      //      case _ => throw new RuntimeException(s"UNK file, $sub in $dir")
//    }
//  }
//
//}
