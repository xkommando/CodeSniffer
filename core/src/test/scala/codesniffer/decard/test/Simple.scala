package codesniffer.decard.test

import java.lang.instrument.Instrumentation

/**
  * Created by Bowen Cai on 1/16/2016.
  */
//object Simple extends App {
//  for (i <- 0 until 5)
//    println(i)
//
//  val arr = Array(1, 3, 5, 8, 10)
//  for (i <- arr) {
//    println(i)
//  }
//}

object Simplee {
  def main(args: Array[String]) {

    for (i <- 0 until 5)
      println(i)

    var i = 0
    while (i < 5) {
      println(i)
      i+=1
    }
//    val arr = Array(1, 3, 5, 8, 10)
//
//    for (i <- arr) {
//      println(i)
//    }
  }
}
//object MyAgent {
//  def premain(args: String, inst: Instrumentation) {
//    val array = new Array[Object](10)
//    val obj = new Object()
//    val size = inst.getObjectSize(obj)
//    System.out.println("Bytes used by object: " + size)
//  }
//}