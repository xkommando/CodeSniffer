package codesniffer.core

import net.liftweb.json.JsonAST.JString
import net.liftweb.json._

/**
 * @author BowenCai
 * @since  15/12/2014.
 */

case object StackTraceSerializer extends CustomSerializer[StackTraceElement](format => (
  {
    case JObject(List(JField("file",JString(file)),
                          JField("class",JString(_c)),
                          JField("function",JString(_f)),
                          JField("line",JInt(_l)))) => new StackTraceElement(_c, _f, file, _l.toInt)
  },
  {
    case d: StackTraceElement => JObject(List(JField("file",JString(d.getFileName)),
      JField("class",JString(d.getClassName)),
      JField("function",JString(d.getMethodName)),
      JField("line",JInt(d.getLineNumber))))
  }
  ))

object JSON {

  val fmt = DefaultFormats + StackTraceSerializer
}