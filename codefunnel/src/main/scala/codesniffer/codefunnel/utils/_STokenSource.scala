package codesniffer.codefunnel.utils

import org.antlr.v4.runtime.{CharStream, Token, TokenFactory, TokenSource}

/**
  * Created by Bowen Cai on 2/24/2016.
  */
class _STokenSource (val tokens: Array[SToken] ) extends TokenSource {
  private var index = 0
  private var eofToken: SToken = null

  override def getLine: Int = {
    if (index < tokens.length)
      tokens(index).getLine
    else if (eofToken != null)
      eofToken.getLine
    else 1
  }

  override def getSourceName: String = ???

  override def nextToken(): Token = {
    if (eofToken != null)
      return eofToken
    val t = tokens(index)
    if (index == tokens.length - 1)
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
    else 0
  }


  override def getInputStream: CharStream = ???

  override def setTokenFactory(factory: TokenFactory[_]): Unit = ???

}
