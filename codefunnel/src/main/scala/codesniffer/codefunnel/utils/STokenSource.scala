package codesniffer.codefunnel.utils

import org.antlr.v4.runtime.{CharStream, Token, TokenFactory, TokenSource}

/**
  * Created by Bowen Cai on 2/23/2016.
  */
class STokenSource(val tokens: Array[SToken] ) extends TokenSource {

  private var index = 0
  private var eofToken: SToken = null

  override def getLine: Int = {
    if (index < tokens.length)
      tokens(index).getLine
    else if (eofToken != null)
      eofToken.getLine
    else if (tokens.length > 0) {
      val lastToken = tokens(tokens.length - 1)
      var line = lastToken.getLine
      val tokenText = lastToken.getText
      if (tokenText != null) {
        var i = 0
        while (i < tokenText.length) {
          if (tokenText.charAt(i) == '\n')
            line += 1
          i += 1
        }
      }
      line
    }
    else 1
  }

  override def getSourceName: String = ???

  override def nextToken(): Token = {
    if (index >= tokens.length) {
      if (eofToken == null) {
        var start = -1
        if (tokens.length > 0) {
          val previousStop: Int = tokens(tokens.length - 1).getStopIndex
          if (previousStop != -1) {
            start = previousStop + 1
          }
        }
        val stop = Math.max(-1, start - 1)
        eofToken = new SToken(-1, Token.EOF, getLine, getCharPositionInLine, Token.DEFAULT_CHANNEL, start, stop, "EOF")
//        eofToken = new SToken(-1, Token.EOF, getLine, getCharPositionInLine, Token.DEFAULT_CHANNEL, "EOF")
      }
      return eofToken
    }
    val t = tokens(index)
    if (index == tokens.length - 1 && t.getType == Token.EOF)
      eofToken = t
    index += 1
    t
  }

  override def getTokenFactory: TokenFactory[_] = ???

  override def getCharPositionInLine: Int = {

    if (index < tokens.length)
      tokens(index).getCharPositionInLine
    else if (eofToken != null)
      eofToken.getCharPositionInLine
    else if (tokens.length > 0) {
      val lastToken = tokens(tokens.length - 1)
      val tokenText = lastToken.getText
      if (tokenText != null) {
        val lastNewLine = tokenText.lastIndexOf('\n')
        if (lastNewLine >= 0) {
          return tokenText.length - lastNewLine - 1
        }
      }
      lastToken.getCharPositionInLine + lastToken.getStopIndex - lastToken.getStartIndex + 1
    }
    else 0
  }


  override def getInputStream: CharStream = ???

  override def setTokenFactory(factory: TokenFactory[_]): Unit = ???
}
