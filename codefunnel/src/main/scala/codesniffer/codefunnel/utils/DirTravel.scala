package codesniffer.codefunnel.utils

import java.io.{File, FileNotFoundException}
import java.util.regex.{Matcher, Pattern}

import com.caibowen.gplume.misc.Str.Utils._
import net.liftweb.json.JsonAST.{JArray, JField, JObject, JString}

import scala.collection.mutable.ArrayBuffer


/**
  * generate json object representing directory structure
  * Created by Bowen Cai on 2/24/2016.
  */
class DirTravel {

  /**
    * true -> accept
    */
  var acceptFile: File=>Boolean = f=>true
  var acceptFolder: File=>Boolean = f=>true

  var pathPrefixToTrim:String = ""
  private var trimIdx = -1

  private def getPath(file: File): String = {
    val p = _PTN.matcher(file.getParent).replaceAll(_SLASH)
    if (trimIdx > 0)
      p.substring(trimIdx)
    else p
  }

  private def travelDir(dir: File): JObject = {
//    val c = dir.listFiles().filter(_.exists()).collect {
//      case file if file.isFile && acceptFile(file) =>
//        JObject(List(JField("type", JString("File")), JField("path", JString(getPath(file))), JField("name", JString(file.getName))))
//      case folder if folder.isDirectory && acceptFolder(folder) =>
//       travelDir(folder)
//    }
    val fls = dir.listFiles()
    val sub = fls.foldLeft(new ArrayBuffer[JObject](fls.length)){(buf, f)=>
      if (f.isFile && acceptFile(f))
        buf += JObject(List(JField("type", JString("File")), JField("path", JString(getPath(f))), JField("name", JString(f.getName))))
      else if (f.isDirectory && acceptFolder(f)){
        val jobj =travelDir(f)
        if (jobj != null)
          buf += jobj
      }
      buf
    }.toList

    if (sub.nonEmpty && sub.head != null) {
      JObject(List(
        JField("type", JString("Folder")),
        JField("path", JString(getPath(dir))), JField("name", JString(dir.getName)),
        JField("children", JArray(sub))
      ))
    } else null.asInstanceOf[JObject]
  }

  val _PTN = Pattern.compile("\\", Pattern.LITERAL)
  val _SLASH = Matcher.quoteReplacement("/")
  def travel(f: File): JObject = {
    if (notBlank(pathPrefixToTrim)) {
      val prefix = _PTN.matcher(f.getParent).replaceAll(_SLASH)
      val p = _PTN.matcher(f.getParent).replaceAll(_SLASH)
      val i = p.indexOf(prefix)
      if (i >= 0)
        trimIdx = i + prefix.length
      else trimIdx = p.length
    }

    val jo = f match {
      case file if f.isFile =>
        JObject(List(JField("type", JString("File")), JField("path", JString(getPath(file))), JField("name", JString(file.getName))))
      case dir if f.isDirectory => travelDir(dir)
      case _ => throw new FileNotFoundException(f.getAbsolutePath)
    }
    trimIdx = -1
    jo
  }
}


