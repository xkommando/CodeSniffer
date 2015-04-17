package codesniffer.test.vgen

import java.io.FileInputStream
import java.lang.reflect.Modifier

import codesniffer.core._
import codesniffer.vgen.MethodVisitor
import com.github.javaparser.JavaParser
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.body.{MethodDeclaration, ClassOrInterfaceDeclaration}
import org.junit.Test
import scala.collection.convert.wrapAsScala._
import scala.collection.mutable.ArrayBuffer
/**
 * Created by Bowen Cai on 4/18/2015.
 */
class VecGenTest {

  @Test
  def t1: Unit = {
    val test = new MethodVisitor()
    val scope = new ClassScope("Src1", null)
    val ctx = new Context(new Config(), new Location("Src1.java", scope, 0))
    val vecList = new ArrayBuffer[CharacVec]

    val cu = JavaParser.parse(new FileInputStream("D:\\__TEMP__\\Src1.java"), "UTF-8", false)
    if (cu.getTypes != null) {
      for (klass <- cu.getTypes if klass.isInstanceOf[ClassOrInterfaceDeclaration]
        && !Modifier.isInterface(klass.getModifiers)) {
        for (method <- klass.getMembers if method.isInstanceOf[MethodDeclaration]) {
          val op = test.visit(method.asInstanceOf[MethodDeclaration], ctx)
          if(op isDefined)
            vecList += op.get
        }
      }
      vecList.foreach(println)

//        //          print("  ")
//        //          println(klass.getName)
//        for (method <- klass.getMembers if method.isInstanceOf[MethodDeclaration]) {
//          test.visit
//        }
//      }
    }
  }
}
