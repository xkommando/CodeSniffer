package codesniffer.deckard.hash

/**
 * Created by Bowen Cai on 4/25/2015.
 */
object Defs {

  val W_default = 4.0
  val K_default = 16

  val UHF_MAIN_INDEX = 0
  // Two hash functions used: main one and a control one.
  val UHF_NUMBER_OF_HASHES = 2
  val UHF_CONTROL1_INDEX = 1

  // Number of precomputed Uns32T words needed to store precomputed
  // hashes of a (part of a) bucket description (more precisely of a <u>
  // function).  It is 2*2 because: need 2 words for each of 1) the main
  // hash; 2) control value 1 hash function (2 words per hash function
  // because a <u> function can occupy two positions in the bucket
  // vector).
  val N_PRECOMPUTED_HASHES_NEEDED = UHF_NUMBER_OF_HASHES * 2

  // 4294967291 = 2^32-5
  val UH_PRIME_DEFAULT = (4294967291L).toInt

  // 2^29
  val MAX_HASH_RND = 536870912

  // 2^32-1
  val TWO_TO_32_MINUS_1 = 4294967295L

}
