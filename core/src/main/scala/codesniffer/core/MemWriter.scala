package codesniffer.core

import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

/**
 * for test
 * Created by Bowen Cai on 4/16/2015.
 */
class MemWriter extends ArrayBuffer[AnyRef] with VecWriter {

  override def write[T: ClassTag] (vec: CharacVec[T]): Unit = {
    super.+=(vec.asInstanceOf[AnyRef])
  }
}
