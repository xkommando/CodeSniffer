package codesniffer.core

import com.caibowen.gplume.common.collection.primitive.Int4CircularList

import scala.beans.BeanProperty

/**
 * @author BowenCai
 * @since  29/11/2014.
 */
@SerialVersionUID(-1151573363320687158L)
class FreqCounter extends Serializable {

  private [this] var OFFSET = System.currentTimeMillis()
  private [this] val record = new Int4CircularList(256)

  /**
   * period in second
   */
  @BeanProperty var period : Double = 1.0

  def count(num : Int) : Unit = {
    val now = (System.currentTimeMillis - OFFSET).toInt
    this.synchronized {
      for (i <- 1 to num) record add now
      trimFrom(now)
    }
  }

  def count() : Unit = {
    val now = (System.currentTimeMillis - OFFSET).toInt
    this synchronized {
      record add now
      trimFrom(now)
    }
  }

  def reset(): Unit = {
    this synchronized {
      record.clear()
      OFFSET = System.currentTimeMillis
    }
  }

  def freq = record.size.toDouble / period

  def freqToNow : Double = {
    val now = (System.currentTimeMillis - OFFSET).toInt
    this.synchronized {
      trimFrom(now)
    }
    freq
  }

  /**
    * discard records earlier than the timePoint
    * @param timePoint a time point from OFFSET, in ms
    */
  private def trimFrom(timePoint : Int) : Unit = {
    val limit = timePoint - (1000 * this.period).toInt
    while (!record.isEmpty && record.front < limit)
      record.popFront()
  }

  def setBufferSize(bufferSize: Int): Unit = {
    record.ensureCapacity(bufferSize)
  }

}


