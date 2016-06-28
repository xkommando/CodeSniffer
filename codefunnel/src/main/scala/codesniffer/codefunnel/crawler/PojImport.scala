package codesniffer.codefunnel.crawler

import java.io.File
import javax.inject.Inject
import javax.sql.DataSource

import codesniffer.codefunnel.utils.{DBUtils, DirTravel}
import gplume.scala.context.AppContext
import gplume.scala.jdbc.DB
import gplume.scala.jdbc.SQLAux._
import net.liftweb.json.{JsonAST, Printer}
import org.postgresql.util.PGobject

import scala.collection.mutable
import scala.collection.convert.wrapAsScala._
import scala.collection.immutable.TreeMap

/**
  * Created by Bowen Cai on 2/24/2016.
  */
object PojImport {

  @Inject
  val ds :DataSource = AppContext.beanAssembler.getBean("dataSource")
  val db = new DB(ds)

  def drive() {

    val cc = AppContext.beanAssembler.configCenter()
    val es = cc.localEntries("poj_import.xml")
//    new TreeMap[String, Int](es).foreach { case (k, v) }
//    println(k + "  " + v)
//  }
    for (e <- asScalaSet(es)) {
      println(e.getKey + "  " + e.getValue)
      updateDir(e.getValue.asInstanceOf[Int], e.getKey)
    }
  }

  def updateDir(pojId: Int, path:String): Unit = {
    val traveler = new DirTravel
    traveler.pathPrefixToTrim = path
    traveler.acceptFolder = folder=> !Set("bin", "target", "output", "lib").contains(folder.getName)
    traveler.acceptFile = file=>file.getName.toLowerCase.endsWith(".java")
    val jsonObj = traveler.travel(new File(path))
    val jsonStr = Printer.compact(JsonAST.render(jsonObj))

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
