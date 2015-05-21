package codesniffer.search

import java.lang.reflect.Modifier

import codesniffer.core.CharacVec
import codesniffer.vgen.{BasicVGen, Context}
import com.github.javaparser.ast.Node
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.visitor.VoidVisitorAdapter
import scala.collection.convert.wrapAsScala._
/**
 * Created by Bowen Cai on 5/21/2015.
 */
class C[F] extends BasicVGen[F] {

  override def collectNodes[E <:Node](nodes: java.util.List[E], vec: CharacVec[F])(implicit ctx: Context[F]): Unit = {
    if (nodes != null && nodes.size > 0)
      for (n <- nodes)
        collectNode(n, vec)(ctx)
  }

  override protected def collectNode(pnode: Node, vec: CharacVec[F])(implicit ctx: Context[F]): Unit = {
    val kids = pnode.getChildrenNodes
    val kidsNodes =  if (kids != null && kids.size() > 0) {
      collectNodes(kids, vec)
      kids.foldLeft(0){(sum, node)=>
        sum + (if (node != null)
          node.getData.asInstanceOf[Int]
        else 0)
      }
    } else 0
    pnode.setData(kidsNodes + 1)
  }
}
