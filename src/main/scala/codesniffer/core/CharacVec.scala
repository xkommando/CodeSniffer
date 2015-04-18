package codesniffer.core

/**
 *
 * @tparam T dimension type
 *
 * Created by Bowen Cai on 4/10/2015.
 */
case class CharacVec[T](indexer: Indexer[T],
                    location: Location,
                    methodName: String,
                    signature: MethodSignature // TODO: replaced with symbol table later
                      ) {

  private var vector: Array[Int] = new Array[Int](64)
  private[this] var _count = 0

  def count = _count
  def validNodeTypeCount = indexer.maxIndex

  /**
   * put one node to this vector, the occurrence count of this type of node will ++
   *
   * @param name
   * @return
   */
  def put(name: T): Int = put(name, 1)

  def put(name: T, number: Int): Int = {
    val idx = indexer.indexOf(name)
    ensureSize(idx + 1)
    vector(idx) += number
    _count += 1
    _count
  }

  /**
   * merge one vector to this one, i.e., accumulate occurrence count
   * @param other
   */
  def merge(other: CharacVec[T]): this.type = {
    if (other.indexer == indexer) {
      val otherSz = other.vector.size
      val minLen = if (otherSz > vector.length) {
        ensureSize(otherSz)
        vector.length
      } else otherSz

      for (idx <- 0 to minLen) {
        vector(idx) += other.vector(idx)
      }
      _count += other.count

    } else throw new IllegalArgumentException(
      s"Could not merge vectors built under different indexer(different coordinates): $this, \r\n$other")
    this
  }

  def +=(other: CharacVec[T]): Unit = merge(other)

  def euclideanDist(other: CharacVec[T]): Double = {
    if (other.indexer == indexer) {
      var sum = 0.0
      for(i <- 0 to indexer.maxIndex) {
        val p = vector(i)
        val dist = other.vector(i) - p
        sum += dist * dist
      }
      math.sqrt(sum)
    }else throw new IllegalArgumentException(
      s"Could not calculate Euclidean distance of two vectors in different coordinates: $this, \r\n$other")
  }

  protected def ensureSize(n: Int): Unit = {
    if (n > vector.length) {
      var newsize = vector.length * 2
      while (n > newsize)
        newsize = newsize * 2

      val newar: Array[Int] = new Array(newsize)
      System.arraycopy(vector, 0, newar, 0, vector.length)
      vector = newar
    }
  }


    /**
     * for debug
     * @return
     */
    override def toString = {
      val sb = new StringBuilder(2048)
      for (i <- 0 until vector.length) {
        val tp = indexer.valueAt(i)
        val name = if (tp.isDefined)
          tp.get
        else "UNK"
        sb.append(name + ":" + vector(i)).append("\r\n")
      }
      sb.toString()
    }
}
