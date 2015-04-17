package codesniffer.test.vgen

import java.io.{File, FileInputStream, FilenameFilter}
import java.lang.reflect.Modifier

import com.github.javaparser.JavaParser
import com.github.javaparser.ast.Node
import com.github.javaparser.ast.body.{ClassOrInterfaceDeclaration, MethodDeclaration}
import com.github.javaparser.ast.stmt.ExpressionStmt
import org.junit.Test

import scala.collection.convert.wrapAsScala._
import scala.collection.mutable.HashMap

/**
 * Created by Bowen Cai on 4/16/2015.
 */
class EnumNodes {

  @Test
  def t(): Unit = {

    val nodeCount = new HashMap[String, Int]

    val dir = new File("D:\\__TEMP__")
    val srcs = dir.listFiles(new FilenameFilter {
      override def accept(dir: File, name: String): Boolean = name.endsWith(".java")}
    )

    for (src <- srcs) {
      val in = new FileInputStream(src)
//      println(src.getName)
      val cu = JavaParser.parse(in, "UTF-8", false)
      if (cu.getTypes != null) {
        for (klass <- cu.getTypes if klass.isInstanceOf[ClassOrInterfaceDeclaration]
                                && !Modifier.isInterface(klass.getModifiers)) {
//          print("  ")
//          println(klass.getName)
          for (method <- klass.getMembers if method.isInstanceOf[MethodDeclaration]) {
            visitMethod(method.asInstanceOf[MethodDeclaration], nodeCount)
          }
        }
      }
      in.close()
    }
    Console.setOut(System.err)
    println(nodeCount.size)
    var c = 0
    for((name, count) <- nodeCount) {
      println(name + " : " + count)
      c += count
    }
    println(c)
  }

  def visitMethod(method: MethodDeclaration, nodeCount: HashMap[String, Int]): Unit =
    if (!Modifier.isAbstract(method.getModifiers)
        && !Modifier.isNative(method.getModifiers)
          && method.getBody != null) {
      val stmts = method.getBody.getStmts
      if (stmts != null) {
//        print("\t")
//        println(method.getName)
        for (s <- stmts)
          visitNode(s, nodeCount)
//        stmts.forEach{(s: Statement)=>
//          visitNode(s.asInstanceOf[Node], nodeCount)
//        }

      }
//      println()
    }

  def visitNode(stmt: Node, nodeCount: HashMap[String, Int]): Unit = {

    val name = if (stmt.isInstanceOf[ExpressionStmt])
      stmt.asInstanceOf[ExpressionStmt].getExpression.getClass.getSimpleName
    else stmt.getClass.getSimpleName
    if (name == "EmptyStmt"
        || name == "ThisExpr") {
      return
    }

    //          print("\t   ")
    //          print(name)
    val e = nodeCount.get(name)
    if (e.isDefined) {
      nodeCount.put(name, e.get + 1)
    } else {
      nodeCount.put(name, 1)
    }


//    nodeCount.put(name, nodeCount.getOrElse(name, 0) + 1)
    val c = stmt.getChildrenNodes
    if (c.size > 0) {
      for(n <- c) {
        visitNode(n, nodeCount)
      }
    }
  }


    //          if (stmt.isInstanceOf[ExpressionStmt] )
    //            printExpStmt(stmt.asInstanceOf[ExpressionStmt])
    //          if (stmt.isInstanceOf[EmptyStmt] || stmt.isInstanceOf[LabeledStmt]) {
    //            print(method.getParentNode.asInstanceOf[ClassOrInterfaceDeclaration].getName)
    //            print("  ")
    //            print(method.getName)
    //            print("   ")
    //            print(stmt.getEndLine)
    //            println(stmt.getClass.getSimpleName)
    ////            println(stmt)
    //          }


}
/*

VariableDeclarationExpr


* assign
* method call
* ??? logger?
*
*
* */
