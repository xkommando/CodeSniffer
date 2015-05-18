package codesniffer.core

/**
 * toString patterns:
 *
 * compilationUnit.java ln:4 package1.package2.class1$class2->method1/class3->method2
 *
 * scope is organised as tree,
 * one location can be represented as a linked list if trace back to the root
 *
 * Created by Bowen Cai on 4/12/2015.
 */
sealed trait Scope {
  def parent: Scope
}

//  cu.java ln:4 pack.class1$class2.method1/class3.method2

final case class PackageScope(name: String) extends Scope {
  override def parent = null
  override def toString = name
}

final case class ClassScope(name: String, parent: Scope) extends Scope {
  override def toString = if (parent == null) name else parent match {
    case a: PackageScope => parent.toString + "." + name
    case a: ClassScope => parent.toString + "$" + name
    case a: MethodScope => parent.toString + "/" + name
    case _ => name
  }
}

final case class MethodScope(name: String, parent: Scope) extends Scope {
  require(parent.isInstanceOf[ClassScope])
  override def toString = parent.toString + "->" + name
}


case class Location(file: String, line: Int, scope: Scope) {

  /**
   *
   * enter a new scope, e.g., append new node to linked list, and return this list (a new location)
   *
   * @param name
   * @param ln
   * @return
   */
  def enterClass(name: String, ln: Int): Location = {
    val ns = new ClassScope(name, this.scope)
    copy(line = ln, scope = ns)
  }

  def enterClass(name: String, nfile: String, ln: Int): Location = {
    val ns = new ClassScope(name, this.scope)
    copy(file = nfile, line = ln, scope = ns)
  }

  def enterMethod(name: String, ln: Int) =
    copy(scope = new MethodScope(name, this.scope), line = ln)


  override def toString = s"$file ln:$line\t$scope"

//  /**
//   * pop tail and leave this scope
//   *
//   */
//  def upperLocation(): Location =
//    copy(scope = this.scope.parent)
}

