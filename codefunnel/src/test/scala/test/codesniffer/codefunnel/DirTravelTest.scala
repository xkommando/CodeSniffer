package test.codesniffer.codefunnel

import java.io.{File, FileInputStream}
import java.lang.reflect.Modifier

import codesniffer.codefunnel.utils.DirTravel
import net.liftweb.json.{DefaultFormats, JsonAST, Printer}


/**
  * Created by Bowen Cai on 2/24/2016.
  */
object DirTravelTest {

  def main(args: Array[String]): Unit = {
    val sm = new SecurityManager
    System.setSecurityManager(sm)
    t2()
//    t3()
  }
  def t2 (): Unit = {
//    var cl = getClass.getClassLoader
//    while (cl != null) {
//      println(cl)
//      cl = cl.getParent
//    }
    val fs = new FileInputStream("D:\\tmp\\Example3.java")
    val mr = classOf[FileInputStream].getDeclaredMethod("read0")
    if (Modifier.isPrivate(mr.getModifiers))
      mr.setAccessible(true)
    var i32 = mr.invoke(fs)
    println(i32)
    i32 = mr.invoke(fs)
    println(i32)
    fs.close()
  }

  def t3(): Unit = {
    val fs = new FileInputStream("D:\\tmp\\Example3.java")
    val mr = classOf[FileInputStream].getDeclaredMethod("read0")
//    if (Modifier.isPrivate(mr.getModifiers))
//      mr.setAccessible(true)
    val i32 = mr.invoke(fs)
    println(i32)
    fs.close()
  }

  def t1 (args: Array[String]) {
    //    val path = "C:\\workSpace\\idea\\CodeSniffer\\codefunnel\\src"
    val path = "codefunnel/src"
    val traveler = new DirTravel
    traveler.acceptFile = f => !f.getName.endsWith(".xml")
    val f = traveler.travel(new File(path))
    implicit val fmt = DefaultFormats
    val json = Printer.pretty(JsonAST.render(f))
    println(json)
  }
}
//{
//  "data": [ {
//  "type": "folder",x
//  "name": "animals",
//  "path": "/animals",
//  "children": [ {
//          "type": "folder",
//          "name": "cat",
//          "path": "/animals/cat",
//          "children": [ {
//                "type": "folder",
//                "name": "images",
//                "path": "/animals/cat/images",
//                "children": [ {
//                          "type": "file",
//                          "name": "cat001.jpg",
//                           "path": "/animals/cat/images/cat001.jpg"
//                           }, {
//                            "type": "file",
//                             "name": "cat001.jpg",
//                              "path": "/animals/cat/images/cat002.jpg"
//                            }
//                          ]
//                    }
//                ]
//            }
//        ]
//    }
//  ]
//}
