package codesniffer.codefunnel.utils

import codesniffer.java8.Java8Parser
import org.antlr.v4.runtime.{CharStream, Token, TokenSource}


/**
  * Created by Bowen Cai on 2/23/2016.
  */
case class SToken(index: Int, tp: Int, line: Int, column: Int, channel: Int, startIdx: Int, stopIdx: Int, text: String) extends Token {

//  println(text + "  " + tp + "   " + Java8Parser.VOCABULARY.getSymbolicName(tp))

  override def getType: Int = tp

  override def getLine: Int = line

  override def getStartIndex: Int = startIdx

  override def getStopIndex: Int = stopIdx

  override def getTokenIndex: Int = index

  override def getChannel: Int = channel// Token.DEFAULT_CHANNEL

  override def getText: String = text

  override def getCharPositionInLine: Int = column

  override def getTokenSource: TokenSource = ???

  override def getInputStream: CharStream = ???

}
