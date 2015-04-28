package codesniffer.hash

import codesniffer.core._

import scala.util.Random

/**
 * Created by Bowen Cai on 4/25/2015.
 */
case class HashParam(successProb: Double,
                      dimension: Int,
                      paramR: Double,
                      paramK: Int,
                      paramM: Int,
                      paramL: Int,
                      paramW: Double,
                      paramT: Int)

object Session {

}
class Session(val successProb: Double,
              val thresholdR: Double,
              val vectors: Seq[CharacVec[_]], val indexer: Indexer[_]) extends LifeCycle { //, reader: VecReader, writer: VecWriter

  import Session._

  var _hashParam: HashParam = _
  def hashParam = _hashParam

  val random = new Random()


  private def getOptimizedParam(): HashParam = ???


  // computeOptimalParameters
  def init(sampleQueries: Seq[CharacVec[_]]): Unit = { // return LSHTable ???
    val param : HashParam = getOptimizedParam()

//    for (i <- 0 until nHFTuples) {
//      val row = new Array[ULSHVec](hfTupleLen)
//      for (j <- 0 until hfTupleLen) {
//        row(j) = generateULSHFunc(param.dimension, param.paramW)
//      }
//      pULSHVecs(i) = row
//    }

  }

  override def start(): Unit = {

    super.start()
  }

  override def stop(): Unit ={

    super.stop()
  }

}
