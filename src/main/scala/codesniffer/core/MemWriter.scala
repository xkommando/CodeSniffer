package codesniffer.core

import scala.collection.mutable.ArrayBuffer

/**
 * for test
 * Created by Bowen Cai on 4/16/2015.
 */
class MemWriter extends ArrayBuffer[CharacVec] with VecWriter{

  override def write(vec: CharacVec): Unit = {
    super.+=(vec)
  }
}
