package codesniffer.codefunnel

import java.lang.reflect.{Field, Modifier}
import java.util.concurrent.Executors
import javax.annotation.Nonnull
import codesniffer.codefunnel.crawler.PojImport
import codesniffer.codefunnel.utils.DBUtils
import gplume.scala.context.AppContext

/**
  * Created by bowen on 3/13/2016.
  */
object ProcSucker {
  def findSingletonObject(@Nonnull klass: Class[_]): AnyRef = {
    val mdKls = try Class.forName(klass.getCanonicalName + "$")
    catch {
      case cn: ClassNotFoundException => null.asInstanceOf[Class[_]]
    }
    if (mdKls != null && klass.getDeclaredFields.isEmpty
      && (!klass.getDeclaredMethods.exists { m =>
      val modi = m.getModifiers
      !Modifier.isPublic(modi) || !Modifier.isStatic(modi)
    })) {

      val fdSObj = try mdKls.getDeclaredField("MODULE$")
      catch {
        case nf: NoSuchFieldException => null.asInstanceOf[Field]
      }
      if (fdSObj != null && fdSObj.getType == mdKls) {
        val modi = fdSObj.getModifiers
        if (Modifier.isPublic(modi) && Modifier.isStatic(modi)) {
          val realObj = fdSObj.get()
          require(realObj != null)
          return realObj
        }
      }
    }
    null.asInstanceOf[AnyRef]
  }

  def main(args: Array[String]) {
    val kls: Class[_] = Class.forName("codesniffer.codefunnel.crawler.PojImport")
    val rbj = findSingletonObject(kls)
    println(rbj)
    println(rbj.getClass)
//    val cf = AppContext.beanAssembler.configCenter()
//    println(cf)
//
//    val ss = cf.localEntries("bellon_import.xml")

//    val pc = Runtime.getRuntime.availableProcessors()
//    println(pc)
//    val exe = Executors.newFixedThreadPool(pc)
//    for (i <- 0 until pc)
//      exe.submit(new Runnable {
//        override def run(): Unit = {
//          var i = -1
//          while (true) {
//            i += 1
//          }
//          println(i)
//        }
//      })
  }
}
