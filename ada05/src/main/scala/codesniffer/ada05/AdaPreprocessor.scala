package codesniffer.ada05

import java.io.{InputStream, File, FileInputStream, InputStreamReader}
import java.lang.{StringBuilder => JStrBuilder}

import org.antlr.v4.runtime
import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}
import org.apache.commons.io.{IOUtils, FileUtils}

import scala.collection.JavaConversions._

/**
 * Created by Bowen Cai on 8/1/2015.
 */

class AdaPreprocessor {

  val encoding = "UTF-8"

//  val KEY_WORDS = {
//    val f = classOf[Ada05Lexer].getField("_SYMBOLIC_NAMES")
//    f.setAccessible(true)
//    val strs = f.get().asInstanceOf[Array[String]]
//    //    strs.foreach(println)
//    strs
//  }

  def preprocess(srcIn: InputStream, ctx: PreprocessContext): JStrBuilder = {

    val nSrc = new JStrBuilder(IOUtils.toString(srcIn, encoding))
    //    println(nSrc)

    val antlrStream = new runtime.ANTLRInputStream(srcIn)
    val lex = new Ada05Lexer(antlrStream)
    val tokens = new CommonTokenStream(lex)

    val 托肯序列 = lex.getAllTokens
    var srcOffset = 0

    var idx = 0
    while (idx != 托肯序列.length) {
      var tk = 托肯序列.get(idx)
      var typeCode = tk.getType
      if (Ada05Lexer.ABORT <= typeCode && typeCode < Ada05Lexer.USE) {
        // keywords
        val upCaseKW = Ada05Lexer._SYMBOLIC_NAMES(typeCode)///////////////////////////////////////
        val start = tk.getStartIndex
        val end = tk.getStopIndex
        nSrc.replace(start - srcOffset, end + 1 - srcOffset, upCaseKW)
      }
      else {
        val start = tk.getStartIndex
        val end = tk.getStopIndex
        typeCode match {
          case Ada05Lexer.EndOfLineComment =>
            nSrc.delete(start - srcOffset, end + 1 - srcOffset)
            srcOffset += end - start + 1

          case Ada05Lexer.WITH =>
            idx += 1
            tk = 托肯序列.get(idx)
            typeCode = tk.getType
            while (typeCode == Ada05Lexer.Identifier) {
              val name = tk.getText
//              println("With " + name)
//              println(tk.getTokenSource.getSourceName)
              idx += 1
              tk = 托肯序列.get(idx)
              typeCode = tk.getType
            }

            // skip the rest
            while (typeCode != Ada05Lexer.SEMI) {
              idx += 1
              tk = 托肯序列.get(idx)
              typeCode = tk.getType
            }
            idx += 1 // skip semi


          case Ada05Lexer.USE =>

          case Ada05Lexer.Identifier =>
            val idLow = tk.getText.toLowerCase
            nSrc.replace(start - srcOffset, end + 1 - srcOffset, idLow)
          case _ =>
        }
      }

      idx += 1
    } // while

    nSrc
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

