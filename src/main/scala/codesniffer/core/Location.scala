package codesniffer.core

/**
 * toString patterns:
 *
 * compilationUnit.java ln:4 package1.package2.class1$class2->method1/class3->method2
 *
 * scope is organised as tree,
 * one location can be represented as a linked list if trace back to the root
 *
 * Created by Bowen Cai on 4/17/2015.
 */
sealed trait Scope{
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
  }
}

final case class MethodScope(name: String, parent: Scope) extends Scope {
  require(parent.isInstanceOf[ClassScope])
  override def toString = parent.toString + "->" + name
}

case class Location(file: String, var scope: Scope, line: Int) {
  override def toString = s"$file ln:$line $scope"

  /**
   * enter a new scope, e.g., append new node to linked list
   * @param next
   */
  def enter(next: ClassScope): Unit = this.scope = new ClassScope(next.name, this.scope)
  def enterClass(name: String): Unit = this.scope = new ClassScope(name, this.scope)
  def enter(next: MethodScope): Unit = this.scope = new MethodScope(next.name, this.scope)
  def enterMethod(name: String): Unit = this.scope = new MethodScope(name, this.scope)


  /**
   * pop tail and leave this place
   */
  def leave(): Scope = {
    val last = this.scope
    this.scope = this.scope.parent
    last
  }
}

