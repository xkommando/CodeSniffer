package codesniffer.deckard

/**
 * Created by Bowen Cai on 8/2/2015.
 */
final case class PackageScope(name: String) extends Scope {
  override def parent = null
  override def toString = name
}
