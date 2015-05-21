package codesniffer.core

import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

/**
 * for test
 * Created by Bowen Cai on 4/16/2015.
 */
class MemWriter[T] extends ArrayBuffer[CharacVec[T]] with VecWriter[T] {

  override def write(vec: CharacVec[T]): Unit = {
    super.+=(vec)
  }
}
