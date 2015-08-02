package codesniffer.deckard

/**
 * Created by Bowen Cai on 8/2/2015.
 */
final case class ClassScope(name: String, parent: Scope) extends Scope {
  override def toString = if (parent == null) name else parent match {
    case a: PackageScope => parent.toString + "." + name
    case a: ClassScope => parent.toString + "$" + name
    case a: MethodScope => parent.toString + "/" + name
    case _ => name
  }
}
