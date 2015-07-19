package codesniffer.vgen

import java.lang.reflect.Modifier

import codesniffer.core.{WeightedVec, CharacVec, ArrayVec}
import codesniffer.api.Node
import codesniffer.api.body.MethodDeclaration
import codesniffer.api.stmt.Statement
import codesniffer.api.visitor.VoidVisitorAdapter

import scala.StringBuilder
import scala.beans.BeanProperty
import scala.collection.mutable
import scala.collection.convert.wrapAsJava._
import scala.collection.mutable.Buffer

/**
 * Created by Bowen Cai on 5/21/2015.
 */
case class SlicerVecGen[F](vecGen: BasicVecGen[F], counter: SubTreeCounter[F]) extends VoidVisitorAdapter[Context[F]] {

  // less than lowerBound -> ignored
  @BeanProperty var lowerBound = 10
  // greater than upperBound -> break
  @BeanProperty var upperBound = 20

  override def visit(method: MethodDeclaration, ctx: Context[F]): Unit =
    if (!ctx.config.filterMethod(method)) {
      val modifiers = method.getModifiers
      if (!Modifier.isAbstract(modifiers) && !Modifier.isNative(modifiers)
        && method.getBody != null
        && method.getBody.getStmts != null && method.getBody.getStmts.size() > 0) {

        val methodName = method.getName

        val prevLoc = ctx.currentLocation
        ctx.currentLocation = ctx.currentLocation.enterMethod(methodName, method.getBeginLine, method.getEndLine)

        try {
          val head: Node = method.getBody
          val total = counter.nodeCount(head)(ctx)
          if (total > lowerBound) {
            if (total < upperBound)
              genVec(head, ctx)(method)
            else search(head, ctx)(method)
          }
        } catch {
          case e: Exception => throw new RuntimeException(s"Could not travel though method ${ctx.currentLocation}", e)
        }
        ctx.currentLocation = prevLoc
      }
    }

  @inline
  private def countWithin(n: Node): Int = {
    val oc = n.getData
    if (oc != null) oc.asInstanceOf[Int]
    else 1
  }

  /**
   *
   * @param node node number under this node is greater than upperBound
   * @param ctx
   * @param method
   * @return
   */
  @inline
  protected def search(node: Node, ctx: Context[F])(implicit method: MethodDeclaration): Unit = {
    var kids = node.getChildrenNodes
    if (kids != null && kids.size() > 0) {
      while (kids.size() == 1)
        kids = kids.get(0).getChildrenNodes
      travel(kids, ctx)
    }
  }

  protected def travel(stmts: java.util.List[Node], ctx: Context[F])(implicit method: MethodDeclaration): Unit = {
    var head = stmts.get(0)
    var i = 1
    var cc = countWithin(head)
    // find an appropriate head
    while (cc > upperBound && i < stmts.size()) {
      search(head, ctx)
      head = stmts.get(i)
      i += 1
      cc = countWithin(head)
    }

    // init
    var sWindow = new mutable.ListBuffer[Node] // the sliding window
    sWindow += head
    var sum = cc

    // travel though this level
    while (i < stmts.size()) {
      // enrich window
      while (sum < lowerBound) {
        if (i >= stmts.size())
          return

        var next = stmts.get(i)
        i += 1
        var ccc = countWithin(next)

        // skip big node, abandon the already-in
        while (ccc > upperBound) {
          search(next, ctx)
          if (i < stmts.size()) {
            next = stmts.get(i)
            i += 1
            ccc = countWithin(next)
            sWindow.clear()
            sum = 0
          } else return
        }
        // add to window
        sWindow += next
        sum += ccc
      }

      // generate A vector
      genVec(sWindow, ctx)

      // chop head
      if (sum >= upperBound) {
        while (sum >= upperBound) {
          sum -= countWithin(sWindow.head) // pop extra nodes
          sWindow.remove(0, 1)
        }
      } else {
        sum -= countWithin(sWindow.head) // pop at least one node, forwarding the window
        sWindow.remove(0, 1)
      }

    }
    // take the rest
    if (sum > lowerBound) {
      genVec(sWindow, ctx)
    }
  }

  @inline
  protected def genVec(node: Node, ctx: Context[F])(implicit method: MethodDeclaration): Unit = {
    val prev = ctx.currentLocation
    ctx.currentLocation = ctx.currentLocation.copy(lineBegin = node.getBeginLine, lineEnd = node.getEndLine)
//    val v = new WeightedVec[F](vecGen.before(method, ctx))
    val v = vecGen.before(method, ctx)

    ctx.currentLocation = prev

    val nodeCode = node.toString.intern()
    v.data = Some(nodeCode)

    vecGen.collectNode(node, v)(ctx)
    vecGen.after(method, v, ctx)
//    println(node)
//    println(va)
  }

  @inline
  protected def genVec[E <: Node](nodes: mutable.Buffer[E], ctx: Context[F])(implicit method: MethodDeclaration): Unit = {
    if (nodes.length > 0) {
      val prev = ctx.currentLocation
      ctx.currentLocation = ctx.currentLocation.copy(lineBegin = nodes.head.getBeginLine, lineEnd = nodes.last.getEndLine)
      val v = vecGen.before(method, ctx)
      ctx.currentLocation = prev

      val nodeCode = nodes.addString(new StringBuilder(512), "\r\n").toString().intern()
      v.data = Some(nodeCode)

      vecGen.collectNodes(nodes, v)(ctx)
      vecGen.after(method, v, ctx)

//      nodes.foreach(println)
//      println(va)
    }

  }
}

//
//  protected def scan[E <:Node](topStmts: java.util.List[E], ctx: Context[F])(implicit method: MethodDeclaration): Unit = {
//
//    val topC = counter.kidsCount(topStmts)(ctx)
//    if (topC > 15) {
//      var buf = new mutable.ListBuffer [Node]
//      buf += topStmts.get(0)
//      var i = 1
//
//      while (i < topStmts.size()) {
//        var sum = topStmts.get(0).getData.asInstanceOf[Int]
//        while (sum < avg) {
//          val next = topStmts.get(i)
//          buf += next
//          i += 1
//          sum += next.getData.asInstanceOf[Int]
//        }
//        slide(buf, ctx)
//        while (sum >= avg) {
//          sum -= buf.last.getData.asInstanceOf[Int]
//          buf.remove(buf.size - 1, 1)
//        }
//
//        if (sum > 5) {
//          val v = null.asInstanceOf[ArrayVec[F]]
//          vecGen.collectNodes(buf, v)(ctx)
//          ctx.vecWriter.write(v)
//        }
//      }
//
//      for (st <- topStmts) {
//        topStmts.get(1)
//      }
//
//    } else if (topC > 5) {
//      //          write
//    }
//  }


