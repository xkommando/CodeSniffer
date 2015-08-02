package codesniffer.deckard

/**
 * Created by Bowen Cai on 8/2/2015.
 */
case class Location(file: String, lineBegin: Int, lineEnd: Int, scope: Scope) {

  /**
   *
   * enter a new scope, e.g., append new node to linked list, and return this list (a new location)
   *
   * @param name
   * @param ln
   * @return
   */
  def enterClass(name: String, lnB: Int, lnE: Int): Location = {
    val ns = new ClassScope(name, this.scope)
    copy(lineBegin = lnB, lineEnd = lnE, scope = ns)
  }

  def enterClass(name: String, nfile: String, lnB: Int, lnE: Int): Location = {
    val ns = new ClassScope(name, this.scope)
    copy(file = nfile, lineBegin = lnB, lineEnd = lnE, scope = ns)
  }

  def enterMethod(name: String, lnB: Int, lnE: Int) =
    copy(scope = new MethodScope(name, this.scope), lineBegin = lnB, lineEnd = lnE)

  override def toString = s"$file ln:$lineBegin to $lineEnd\t$scope"

//  /**
//   * pop tail and leave this scope
//   *
//   */
//  def upperLocation(): Location =
//    copy(scope = this.scope.parent)
}
