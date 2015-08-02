package codesniffer.deckard

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
trait Scope {
  def parent: Scope
}
