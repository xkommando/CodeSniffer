package codesniffer.deckard

/**
 * Created by Bowen Cai on 4/13/2015.
 */
trait VecWriter[T] {
  def write(vec: CharacVec[T]): Unit
}
