package codesniffer.deckard

/**
 * Created by Bowen Cai on 8/2/2015.
 */
final case class MethodScope(name: String, parent: Scope) extends Scope {
  require(parent.isInstanceOf[ClassScope])
  override def toString = parent.toString + "->" + name
}
