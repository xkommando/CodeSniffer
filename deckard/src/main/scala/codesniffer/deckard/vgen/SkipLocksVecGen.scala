package codesniffer.deckard.vgen

import codesniffer.deckard.CharacVec
import codesniffer.api.Node
import codesniffer.api.expr.MethodCallExpr
import codesniffer.api.stmt._

import scala.collection.convert.wrapAsScala._

/**
 *
 * simply skip SynchronizedStmt and lock-try-finally-unlock pattern
 *
 * Created by Bowen Cai on 5/17/2015.
 */
class SkipLocksVecGen[F] extends BasicVecGen[F] {

  val locks = new ThreadLocal[Int]()
  locks.set(0)


  override def putNode(node: Node, ctx: Context[F]): Unit = ???
//    node match {
//      case call: MethodCallExpr =>
//        call.getName match {
//          case "lock" => locks.set(locks.get() + 1)
//          case "unlock" => locks.set(locks.get() - 1)
//          case _ => addMethodCall(call, vec)
//        }
//      case tryStmt: TryStmt =>
//        collectNodes(tryStmt.getResources, vec)
//        collectNode(tryStmt.getTryBlock, vec)
//        val ct = tryStmt.getCatchs
//        collectNodes(ct, vec)
//
//        val fb = tryStmt.getFinallyBlock
//        if (fb != null) {
//          val lks = locks.get()
//          collectNode(fb, vec)
//          val lks2 = locks.get()
//          if (lks2 >= lks && ct != null && ct.size > 0) // no unlock op, or got catch claus
//            vec.put("TryStmt".asInstanceOf[F])
//        } else vec.put("TryStmt".asInstanceOf[F])
//
//      case _ =>
//        vec.put(findNodeName(node).asInstanceOf[F])
//        collectNodes(node.getChildrenNodes, vec)
//    }
//  }
}

//        // skip unlock call
//        if (locked.get()) {
//          val ct = tryStmt.getCatchs
//          if (ct != null && ct.size() > 0) vec.put("TryStmt")
//
//          val flyblk = tryStmt.getFinallyBlock
//          if (flyblk != null) {
//            for (fstmt <- flyblk.getStmts) fstmt match {
//              case es: ExpressionStmt => es.getExpression match {
//                case mc: MethodCallExpr =>
//                  if (mc.getName == "unlock") locked.set(false)
//                  else addMethodCall(mc, vec)
//                case _ => collectNode(es, vec)(ctx)
//              }
//              case _ => collectNode(fstmt, vec)(ctx)
//            }
//          }
//        } else collectNode(tryStmt, vec)(ctx)
