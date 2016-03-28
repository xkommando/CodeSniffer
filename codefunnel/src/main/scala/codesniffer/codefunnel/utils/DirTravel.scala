package codesniffer.codefunnel.utils

import java.io.{File, FileNotFoundException}

import com.caibowen.gplume.misc.Str.Utils._
import net.liftweb.json.JsonAST.{JArray, JField, JObject, JString}


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

  var trimPath:String = ""
  private var trimIdx = -1
  private def getPath(file:File):String =
    if (trimIdx > 0) {
      file.getParent.substring(trimIdx)
    } else file.getParent

  private def travelDir(dir: File): JObject = {
    val c = dir.listFiles().filter(_.exists()).collect {
      case file if file.isFile && acceptFile(file) =>
        JObject(List(JField("type", JString("File")), JField("path", JString(getPath(file))), JField("name", JString(file.getName))))
      case folder if folder.isDirectory && acceptFolder(folder) => travelDir(folder)
    }
    JObject(List(JField("type", JString("Folder")), JField("path", JString(getPath(dir))), JField("name", JString(dir.getName)),
      JField("children", JArray(c.toList))
    ))
  }

  def travel(f: File): JObject = {
    if (notBlank(trimPath)) {
      val p = f.getParent
      val i = p.indexOf(trimPath)
      if (i >= 0)
        trimIdx = i + trimPath.length
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


