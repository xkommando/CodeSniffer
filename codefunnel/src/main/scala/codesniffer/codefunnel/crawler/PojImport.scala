package codesniffer.codefunnel.crawler

import java.io.File

import codesniffer.codefunnel.utils.{DBSupport, DirTravel}
import gplume.scala.jdbc.SQLAux._
import net.liftweb.json.{JsonAST, Printer}
import org.postgresql.util.PGobject

/**
  * Created by Bowen Cai on 2/24/2016.
  */
object PojImport extends DBSupport {

  def updateDir(pojId: Int, path:String): Unit = {
    super.boot()
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
    super.boot()
    db.newSession{implicit session =>
      val json = sql"SELECT directory FROM project WHERE id = $pojId".first{ rs=>
        rs.getObject(1)
          .asInstanceOf[PGobject]
          .getValue
      }
      println(json)
    }
  }

  def main(args: Array[String]) {
    super.boot()
//    updateDir(1, "D:\\__TEMP__\\eclipse-jdtcore\\src")
    checkDir(1)
//    val conn = datasource.getConnection
//
//    val stmt = conn.createStatement()
//    stmt.setQueryTimeout(999)
//    val rs = stmt.executeQuery("select data, arr from jtest where id = 10")
//    while (rs.next()) {
//      val pgo = rs.getObject(1).asInstanceOf[PGobject]
//      println(pgo.getClass.getCanonicalName)
//      println(pgo)
//      val a2 = rs.getObject(2).asInstanceOf[PgArray] //.getArray //.asInstanceOf[Array[PGobject]]
//      val arr = a2.getArray
//      println(arr)
//    }
//
//    rs.close()
//    stmt.close()
//    conn.close()
  }


}
