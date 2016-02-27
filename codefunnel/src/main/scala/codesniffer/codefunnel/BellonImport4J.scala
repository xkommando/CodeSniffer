package codesniffer.codefunnel

import gplume.scala.jdbc.SQLAux._
import gplume.scala.jdbc.SQLOperation._
import org.slf4j.LoggerFactory

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
  * Created by Bowen Cai on 2/26/2016.
  */
object BellonImport4J extends DBSupport {

  val LOG = LoggerFactory.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    super.boot()
    step1_load(2, "D:\\__TEMP__\\eclipse-ant.cpf")
//    step1_load(1, "D:\\__TEMP__\\eclipse-jdtcore.cpf")
    step2_match_update(2)
    step3_import_clean()
//    step3_clean()
//    val path = "D:\\__TEMP__\\eclipse-ant.cpf"
//    step2_match_update(2)

  }


  def step1_load(poj_id:Int, path: String): Unit = {
    // for .cpf
//    bellon += Seq(cctype, pathF1, linesF1.head, linesF1.last, pathF2, linesF2.head, linesF2.last)
    val bellon = new ArrayBuffer[Seq[Any]](256)
    for (line <- Source.fromFile(path).getLines()) {
      val _idxF1 = line.indexOf(".java") + 5
      if (_idxF1 > 5) {
        // cannot be 0 + 5 = 5, must be > 5
        val pathF1 = line.substring(0, _idxF1)
        val _right1 = line.substring(_idxF1)
        val _rests = _right1.split("\\s+", 4)
        //        val int2 = rests.filter(_.length > 0).take(2).map(Integer.parseInt)
        require(_rests.length == 4)
        val linesF1 = _rests.slice(1, 3).map(Integer.parseInt)
        val _right2 = _rests.last
        val _idxF2 = _right2.indexOf(".java") + 5
        if (_idxF2 > 5) {
          val pathF2 = _right2.substring(0, _idxF2)
          val _rest2 = _right2.substring(_idxF2).split("\\s+")
          val _int3 = _rest2.drop(1).map(Integer.parseInt)
          val linesF2 = _int3.take(2)
          val cctype = _int3.last
          //          println(s"$cctype: $pathF1(${linesF1.head},${linesF1.last})  $pathF2(${linesF2.head},${linesF2.last})")
          bellon += Seq(cctype, pathF1, linesF1.head, linesF1.last, pathF2, linesF2.head, linesF2.last)
        }
      }
      LOG.warn("Failed on " + line)
    }
    LOG.info("Bellon raw data fully parsed!")
    val seqBellon = bellon.toSeq
    println(seqBellon.size)
    db.newSession { implicit session =>
      sql"DROP TABLE IF EXISTS __bellon_temp".execute()
      sql"""CREATE TABLE __bellon_temp(id SERIAL PRIMARY KEY , "type" SMALLINT,
                   pathf1 TEXT, f1_line1 INT , f1_line2 INT , match_pc_id1 INT, distance1 INT,
                   pathf2 TEXT, f2_line1 INT , f2_line2 INT , match_pc_id2 INT, distance2 INT)""".execute()

      sql"""INSERT INTO __bellon_temp("type" , pathf1 , f1_line1 , f1_line2, pathf2 , f2_line1 , f2_line2)VALUES (?,?,?,?,?,?,?)""".batchInsert(seqBellon)
//      sql"""UPDATE __bellon_temp AS BF1 SET match_pc_id1 = (SELECT )"""
      LOG.info("All data stored to data base, step 1 finished")
    }
  }

  /**
    * update __bellon_temp set match_pc_id2 = sub.proc_id, distance2 = sub.MT1
from (select PD1.id as proc_id,
	BF2.id as bf2_id,
	(abs(lower(PD1.lines) - BF2.f1_line1) + abs(upper(PD1.lines) - BF2.f1_line2)) AS MT1
    from "procedure" as PD1
     join __bellon_temp as BF2 on position(PD1.file in BF2.pathf1) > 0
    * where pd1.poj_id = 2
    * ORDER BY MT1 ASC LIMIT 1
    * ) as sub  where id = sub.bf2_id

    * outer table is invisible for inner query, so we have to divide the operation and do a select-store-update
    */
  val distance_threshold = 20
  val amplify_ratio = 5
  def step2_match_update(poj_id: Int): Unit = {

    db.newSession { implicit session =>
      val ids = sql"SELECT id FROM __bellon_temp".vector(colInt)

      LOG.info("Step2: match my procedure entries with bellon functions")
      ids.foreach{id=>
        // abs(lower(PD1.lines) - BF1.f1_line1) * $amplify_ratio + abs(upper(PD1.lines) - BF1.f1_line2)
        // why multiple amplify_ratio?
        // sometimes the start line get mismatch too, but from observation the begin-line mismatch is less significant,
        //  not as ridiculous  as the end-line mismatch, so amplify the first mismatch to alleviate the second one
        // Step 2.1, find the best match, for the left function
        val id2_left = sql"""
           select PD1.id as proc_id,
           	(abs(lower(PD1.lines) - BF1.f1_line1) * $amplify_ratio + abs(upper(PD1.lines) - BF1.f1_line2)) AS MT1
               from "procedure" as PD1
                full outer join __bellon_temp as BF1 on position(PD1.file in BF1.pathf1) > 0
              where PD1.poj_id = $poj_id
               and BF1.id = $id
               ORDER BY MT1 ASC limit 1
           """.first{rs=> (rs.getInt(1), rs.getInt(2))}
        // Step 2.2, if it is reasonable, store the match and score(distance) for the left function
        if (id2_left.isDefined) {
          val dist = id2_left.get._2
          if (dist < distance_threshold) {// why this number ? no reason, purely empirical
            val proc_id = id2_left.get._1
            sql"UPDATE __bellon_temp SET match_pc_id1 = $proc_id, distance1 = $dist where id = $id".execute()
          }
        }

        // repeat above two steps for the right function
        val id2_right = sql"""
           select PD2.id as proc_id,
           	(abs(lower(PD2.lines) - BF2.f2_line1) * $amplify_ratio + abs(upper(PD2.lines) - BF2.f2_line2)) AS MT2
               from "procedure" as PD2
                full outer join __bellon_temp as BF2 on position(PD2.file in BF2.pathf2) > 0
              where pd2.poj_id = $poj_id
               and bf2.id = $id
               ORDER BY MT2 ASC limit 1
           """.first{rs=> (rs.getInt(1), rs.getInt(2))}
        if (id2_right.isDefined) {
          val dist = id2_right.get._2
          if (dist < distance_threshold) {
            val proc_id = id2_right.get._1
            sql"UPDATE __bellon_temp SET match_pc_id2 = $proc_id, distance2 = $dist where id = $id".execute()
          }
        }
      } // foreach

      sql"""DELETE FROM __bellon_temp WHERE match_pc_id1 IS NULL OR match_pc_id2 IS NULL""".execute()

      sql"""DELETE FROM __bellon_temp USING __bellon_temp BT
              WHERE __bellon_temp.match_pc_id1 = BT.match_pc_id1 AND __bellon_temp.match_pc_id2 = BT.match_pc_id2 AND
                __bellon_temp.id < BT.id""".execute()

      val countCC = sql"SELECT COUNT(1) FROM __bellon_temp LIMIT 99999".first(colInt).get
      LOG.info(s"$countCC code clone matched and is going to be imported")
    } // db
    LOG.info("Step 2 Finished")
  }

  def step3_import_clean(): Unit = {
    db.newSession{implicit session=>
      sql"TRUNCATE TABLE cclone_bench_bellon CASCADE ".execute()
      LOG.info("cclone_bench_bellon TRUNCATED ")
      sql"""INSERT INTO cclone_bench_bellon(func1_id, func2_id, "type")SELECT match_pc_id1, match_pc_id2,"type" FROM __bellon_temp""".execute()
      LOG.info("all clone data imported")
      sql"DROP TABLE IF EXISTS __bellon_temp CASCADE".execute()
      LOG.info("temp table cleaned")
    }
  }
}

//for (line <- Source.fromFile(path).getLines()) {
//  val _idxF1 = line.indexOf(".java") + 5
//  if (_idxF1 > 5) {
//  // cannot be 0 + 5 = 5, must be > 5
//  val pathF1 = line.substring(0, _idxF1)
//  println(path2pkg(pathF1))
//}
//}  def path2pkg(path:String): String = {
//  val idx = path.indexOf("src/")
//  val p = if (idx >= 0) path.substring(idx + 4)
//  else path
//  val _pkg = p.replaceAll("/", ".")
//  val jidx = _pkg.toLowerCase.lastIndexOf(".java")
//  if (jidx > 0) _pkg.substring(0, jidx) else _pkg
//}