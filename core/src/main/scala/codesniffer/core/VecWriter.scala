package codesniffer.core

import scala.reflect.ClassTag

/**
 * Created by Bowen Cai on 4/13/2015.
 */
trait VecWriter {
  def write[T: ClassTag](vec: CharacVec[T]): Unit
}
