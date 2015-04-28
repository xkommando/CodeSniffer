package codesniffer.hash

/**
 * Created by Bowen Cai on 4/28/2015.
 */
class BucketEntry(pointIndex: Int, var next: BucketEntry)

class GBucket(val controlValue1: Int,
              pointIndex: Int,
              var next: GBucket) {

  val firstEntry: BucketEntry = new BucketEntry(pointIndex, null)

  def addPoint(pointIdx: Int): Unit = {
    val nb = new BucketEntry(pointIdx, firstEntry.next)
    firstEntry.next = nb
  }
}
class UHashStruct(hashTable: Array[GBucket],
                  points: Seq[Point],
                  primeNum: Int,
                  val mainHashA: Array[Int],
                  val controlHash1: Array[Int]) {

  var nHashedBuckets = 0
  var nHashedPoints = 0

  def addBucketEntry(bucketVec1: Array[Int], bucketVec2: Array[Int], pointIdx: Int): Unit = {

    val combineAt = (idx: Int) => {
      val h1 = bucketVec1(idx)
      val h2 = bucketVec1(idx + D.UHF_NUMBER_OF_HASHES)
      val h = h1.toLong + h2.toLong
      (if (h > D.UH_PRIME_DEFAULT)
        h - D.UH_PRIME_DEFAULT
      else h).toInt
    }

    val hIndex = combineAt(D.UHF_MAIN_INDEX) % hashTable.length
    val control1 = combineAt(D.UHF_CONTROL1_INDEX)

    var p = hashTable(hIndex)
    while (p != null && p.controlValue1 != controlHash1) p = p.next
    if (p == null) {
      nHashedBuckets += 1
      val ngb = new GBucket(control1, pointIdx, next = hashTable(hIndex))
      hashTable(hIndex) = ngb
    } else p.addPoint(pointIdx)

    nHashedPoints += 1
  }

}