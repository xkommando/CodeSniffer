package test.codesniffer.codefunnel

import java.sql.DriverManager

import net.liftweb.json.{DefaultFormats, Serialization}
import org.postgresql.jdbc.PgArray
import org.postgresql.util.PGobject

/**
  * Created by Bowen Cai on 2/22/2016.
  */
object PGJsonTest {

  def main(args: Array[String]): Unit = {
//    Class.forName("org.postgresql.Driver")
    var arr = Array("78", "erset", "123", null, "azss", "azssw")
    val naar = arr.collect{case str:String if str != null && str.length > 4 =>
      str
    }
    println(arr = naar)
//    List("sd")
//    t2()
    val a = "44"
    var b: String = null
    println((b = a).getClass.getCanonicalName)
  }

  def t2(): Unit = {
    val conn = DriverManager.getConnection(
      "jdbc:postgresql://localhost:5432/aser_codehouse", "xkom", "123456")

//    val ps = conn.prepareStatement("select src_impl from \"procedure\" where id < 16081 and id > 16078")
    val ps = conn.prepareStatement(" select src_impl from \"procedure\" where \"class\" = 'CharacterLiteral'")
    val rs = ps.executeQuery()
    while (rs.next()) {
      println(rs.getString(1))
    }
  }
  def t1(): Unit = {

    val addr = new Address("str", Some("cty"))
    val au = Author("na", "@", None)
    val article = new Article("t name22222222", "cont", Some(au))
    val jsonStr = Serialization.writePretty(article)(DefaultFormats)
//    println(jsonStr)

    val conn = DriverManager.getConnection(
          "jdbc:postgresql://localhost:5432/aser_codehouse", "xkom", "123456")

    val ps = conn.prepareStatement("insert into jtest(data)values(?)")
    val pgj = new PGobject
    pgj.setType("json")
    pgj.setValue(jsonStr)
    ps.setObject(1, pgj)
    ps.close()

//    ps.executeUpdate()

//    -- CREATE TYPE lex_token AS("type" INT, "line" INT, "index" INT, "text" TEXT);
//    -- alter table jtest add column arr lex_token[]
//      -- insert into jtest(arr)values(ARRAY['(0, 15, 99, "public")'::lex_token, '(6, 154, 299, "class")'::lex_token])

    val stmt = conn.createStatement()
    stmt.setQueryTimeout(999)
    val rs = stmt.executeQuery("select data, arr from jtest where id = 10")
    while (rs.next()) {
      val pgo = rs.getObject(1).asInstanceOf[PGobject]
      println(pgo.getClass.getCanonicalName)
      println(pgo)
      val a2 = rs.getObject(2).asInstanceOf[PgArray] //.getArray //.asInstanceOf[Array[PGobject]]
      val arr = a2.getArray
      println(arr)
    }

    rs.close()
    stmt.close()
    conn.close()
  }

}
