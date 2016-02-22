import java.sql.DriverManager

import net.liftweb.json.{DefaultFormats, Serialization}
import org.postgresql.util.PGobject


case class Address(street: String, city: Option[String])
case class Author(name: String, email: String, address: Option[Address])
case class Article(title: String, content: String, author: Option[Author])

//object ____TTT {
//
//  def main(args: Array[String]) {
//    val addr = new Address("str", Some("cty"))
//    val au = Author("na", "@", None)
//    val article = new Article("t name", "cont", Some(au))
//    for {
//      author <- article.author
//      address <- author.address
//      city <- address.city
//    } yield {
//      println(author.name)
//      println(address.street)
//      println(city.toLowerCase())
//    }
//  }
//}
/**
  * Created by Bowen Cai on 2/22/2016.
  */
object PGJsonTest {

  def main(args: Array[String]) {

    val addr = new Address("str", Some("cty"))
    val au = Author("na", "@", None)
    val article = new Article("t name22222222", "cont", Some(au))
    val jsonStr = Serialization.writePretty(article)(DefaultFormats)
    println(jsonStr)

    Class.forName("org.postgresql.Driver")
    val conn = DriverManager.getConnection(
          "jdbc:postgresql://localhost:5432/aser_codehouse", "xkom", "123456")

    val ps = conn.prepareStatement("insert into jtest(data)values(?)")
    val pgj = new PGobject
    pgj.setType("json")
    pgj.setValue(jsonStr)
    ps.setObject(1, pgj)

    ps.executeUpdate()

//    -- CREATE TYPE lex_token AS("type" INT, "line" INT, "index" INT, "text" TEXT);
//    -- alter table jtest add column arr lex_token[]
//      -- insert into jtest(arr)values(ARRAY['(0, 15, 99, "public")'::lex_token, '(6, 154, 299, "class")'::lex_token])

    val stmt = conn.createStatement()
    val rs = stmt.executeQuery("select data from jtest where id = 4")
    while (rs.next()) {
      val pgo = rs.getObject(1).asInstanceOf[PGobject]
      println(pgo.getClass.getCanonicalName)
      println(pgo)
    }
    ps.close()
    conn.close()
  }

}

//public static void main(String[] argv) throws SQLException
//{
//
//  System.out.println("-------- PostgreSQL "
//    + "JDBC Connection Testing ------------");
//
//  try {
//
//    Class.forName("org.postgresql.Driver");
//
//  } catch (ClassNotFoundException e) {
//
//    System.out.println("Where is your PostgreSQL JDBC Driver? "
//      + "Include in your library path!");
//    e.printStackTrace();
//    return;
//
//  }
//
//  System.out.println("PostgreSQL JDBC Driver Registered!");
//
//  Connection connection = null;
//
//  try {
//
//    connection = DriverManager.getConnection(
//      "jdbc:postgresql://localhost:5432/postgres", "denish", "omniti");
//
//  } catch (SQLException e) {
//
//    System.out.println("Connection Failed! Check output console");
//    e.printStackTrace();
//    return;
//
//  }
//
//  if (connection != null) {
//    System.out.println("You made it, take control your database now!");
//  } else {
//    System.out.println("Failed to make connection!");
//  }
//
//  int id = 2;
//  String data = "{\"username\":\"robert\",\"posts\":100122,\"emailaddress\":\"robert@omniti.com\"}";
//
//  PGobject dataObject = new PGobject();
//  dataObject.setType("json");
//  dataObject.setValue(data);
//
//  try {
//    PreparedStatement stmt = connection.prepareStatement("insert into sample (id, data) values (?, ?)");
//    stmt.setInt(1, id);
//    stmt.setObject(2, dataObject);
//
//    stmt.executeUpdate();
//
//    stmt.close();
//  } catch (SQLException sqle) {
//    System.err.println("Something exploded running the insert: " + sqle.getMessage());
//  }
//}