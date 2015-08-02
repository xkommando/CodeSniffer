package codesniffer.deckard

import scala.collection.mutable.ArrayBuffer

/**
 * for test
 * Created by Bowen Cai on 4/16/2015.
 */
class MemWriter[T] extends ArrayBuffer[CharacVec[T]] with VecWriter[T] {

  override def write(vec: CharacVec[T]): Unit = {
    super.+=(vec)
  }
}
