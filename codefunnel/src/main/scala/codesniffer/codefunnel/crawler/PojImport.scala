package codesniffer.codefunnel.crawler

import java.io.File
import javax.inject.Inject

import codesniffer.codefunnel.utils.{DBUtils, DirTravel}
import gplume.scala.context.AppContext
import gplume.scala.jdbc.SQLAux._
import net.liftweb.json.{JsonAST, Printer}
import org.postgresql.util.PGobject

import scala.collection.mutable
import scala.collection.convert.wrapAsScala._

/**
  * Created by Bowen Cai on 2/24/2016.
  */
object PojImport {

  @Inject
  var map: Map[Int, String] = null
  import DBUtils.db

  def main(args: Array[String]) {
    var ld: ClassLoader = getClass.getClassLoader
    while (ld != null) {
      println(ld.getClass.getCanonicalName)
      ld = ld.getParent
    }
  }
  def drive() {

    val cc = AppContext.beanAssembler.configCenter()
    val es = cc.localEntries("poj_import.xml")

    for (e <- asScalaSet(es)) {
      println(e.getKey + "  " + e.getValue)
    }
    println(map)
    //    updateDir(1, "D:\\__TEMP__\\eclipse-jdtcore\\src")
//    checkDir(1)
  }

  def updateDir(pojId: Int, path:String): Unit = {
    val traveler = new DirTravel
    traveler.trimPath = "D:\\__TEMP__\\"
    traveler.acceptFolder = folder=> !Set("bin", "target", "output", "lib").contains(folder.getName)
    traveler.acceptFile = file=>file.getName.toLowerCase.endsWith(".java")
    val jsonObj = traveler.travel(new File(path))
    val jsonStr = Printer.compact(JsonAST.render(jsonObj))

    println(jsonStr)
    db.newSession{implicit session =>
      sql"UPDATE project SET directory = ? WHERE id = ?".update(ps=>{
        val pgjson = new PGobject
        pgjson.setType("json")
        pgjson.setValue(jsonStr)
        ps.setObject(1, pgjson)
        ps.setInt(2, pojId)
      })
    }
    checkDir(pojId)
  }

  def checkDir(pojId:Int): Unit = {
    db.newSession{implicit session =>
      val json = sql"SELECT directory FROM project WHERE id = $pojId".first{ rs=>
        rs.getObject(1)
          .asInstanceOf[PGobject]
          .getValue
      }
      println(json)
    }
  }


}
