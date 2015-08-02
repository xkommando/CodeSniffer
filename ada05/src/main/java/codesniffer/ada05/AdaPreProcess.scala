package codesniffer.ada05

import java.io.{File, FileInputStream, InputStreamReader}
import java.lang.{StringBuilder => JStrBuilder}

import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}
import org.apache.commons.io.FileUtils

import scala.collection.JavaConversions._

/**
 * Created by Bowen Cai on 8/1/2015.
 */


class PreprocessContext {

}

class AdaPreprocessor {

  val KEY_WORDS = {
    val f = classOf[Ada05Lexer].getField("_SYMBOLIC_NAMES")
    f.setAccessible(true)
    val strs = f.get().asInstanceOf[Array[String]]
    //    strs.foreach(println)
    strs
  }

  def preprocess(srcFile: File): Unit = {
    val encoding = "UTF-8"
    val src = new JStrBuilder(FileUtils.readFileToString(srcFile, encoding))
    //    println(src)

    val antlrStream = new ANTLRInputStream(new FileInputStream(srcFile))
    val lex = new Ada05Lexer(antlrStream)
    val tokens = new CommonTokenStream(lex)

    val 托肯序列 = lex.getAllTokens
    var srcOffset = 0

    var idx = 0
    while (idx != 托肯序列.length) {
      var tk = 托肯序列.get(idx)
      idx += 1

      var typeCode = tk.getType
      if (Ada05Lexer.ABORT <= typeCode && typeCode < Ada05Lexer.USE) {
        // keywords
        val upCaseKW = Ada05Lexer._SYMBOLIC_NAMES(typeCode)
        val start = tk.getStartIndex
        val end = tk.getStopIndex
        src.replace(start - srcOffset, end + 1 - srcOffset, upCaseKW)
      }
      else {
        val start = tk.getStartIndex
        val end = tk.getStopIndex
        typeCode match {
          case Ada05Lexer.EndOfLineComment =>
            src.delete(start - srcOffset, end + 1 - srcOffset)
            srcOffset += end - start + 1

          case Ada05Lexer.WITH =>
            idx += 1
            tk = 托肯序列.get(idx)
            typeCode = tk.getType
//            if (typeCode == Ada05Lexer.Identifier)


            // skip the rest
            while (typeCode != Ada05Lexer.SEMI) {
              idx += 1
              tk = 托肯序列.get(idx)
              typeCode = tk.getType
            }
            idx += 1
          case Ada05Lexer.USE =>

          case Ada05Lexer.Identifier =>
            val idLow = tk.getText.toLowerCase
            src.replace(start - srcOffset, end + 1 - srcOffset, idLow)
          case _ =>
        }
      }
    } // while

    val f = new File("D:\\t.ada")
    if (!f.exists()) {
      f.createNewFile()
    }
    val newSrc = src.toString()
    //    println()
    //    println(newSrc)
    FileUtils.writeStringToFile(f, newSrc, encoding)
  }
}

//  def copyToString(path: String, encoding: String): JStrBuilder = {
//    val stream = new FileInputStream(path)
//    val read = new InputStreamReader(stream, encoding)
//    val buf = new Array[Char](8192)
//    //    FileUtils.readFileToString()
//    val sb = new JStrBuilder(8192)
//    var n = read.read(buf, 0, 8192)
//    while (-1 != n) {
//      sb.append(buf, 0, n)
//      n = read.read(buf, 0, 8192)
//    }
//    sb
//  }

