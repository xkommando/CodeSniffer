package codesniffer.test.core

import codesniffer.core.{ClassScope, MethodScope, PackageScope}
import org.junit.Test

/**
 * Created by Bowen Cai on 4/17/2015.
 */
class LocationTest {

  //  cu.java ln:4 package.class1$class2.method1/class3.method2

  val pkg1 = new PackageScope("pkg1")
//  val pkg2 = new PackageScope("pkg2", Some(pkg1))
  val klass1 = new ClassScope("class1", pkg1)
  val klass2 = new ClassScope("class2", klass1)
  val method1 = new MethodScope("method1", klass2)
  val klass3 = new ClassScope("class3", method1)
  val method2 = new MethodScope("method2", klass3)

  @Test
  def t1: Unit = {
    Console.setOut(System.err)

    println(pkg1)
//    println(pkg2)
    println(klass1)
    println(klass2)
    println(method1)
    println(klass3)
    println(method2)

    println(klass1.copy(parent = null))
    println(klass3.copy())
  }
}
