package test.codesniffer.codefunnel

import java.io.File

import codesniffer.codefunnel.DirTravel
import net.liftweb.json.{JsonAST, Printer, DefaultFormats}

/**
  * Created by Bowen Cai on 2/24/2016.
  */
object DirTravelTest {

  def main (args: Array[String]) {
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
