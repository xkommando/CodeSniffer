package codesniffer.core

/**
 * Created by Bowen Cai on 4/13/2015.
 */
object MethodDescriptor {
  val empty = new MethodDescriptor(2,"UNK", None, None, None) // 2 -> private
}
case class MethodDescriptor(modifier: Int,
                            typeName: String, // why not ref javaparser.Type? javaparser.Type is a node and may cause mem leak
                            parameterTypes: Option[Set[String]],
                           annotations: Option[Set[String]],
                           throws: Option[Set[String]]
                            ) {

def appendTo(sb: StringBuilder): StringBuilder = {
    if (annotations.isDefined)
      annotations.get.addString(sb, "@", " @", " ")

    if (parameterTypes.isDefined)
      parameterTypes.get.addString(sb, "( ", ", ", " )")
    else sb.append("( )")

    sb.append(" => ").append(typeName)

    if (throws.isDefined) {
      sb.append("  throws ")
      throws.get.addString(sb, "( ", ", ", ")")
    }

    sb
  }

  // empirical, no why
  def distance(other: MethodDescriptor): Double = {
    var dist = if (typeName != other.typeName) 0.4 else 0.0
//    if (modifier != other.modifier) dist += 0.4

    dist + {
      parameterTypes match {
        case Some(ps) =>
          other.parameterTypes match {
            case Some(ops) =>
              ps.zipAll(ops, "1", "0").foldLeft(0.0) { (sum, p) =>
                if (p._1 != p._2)
                  sum + 0.2
                else sum
              }
            case None => ps.size / 5
          }
        case None => if (other.parameterTypes.isDefined) other.parameterTypes.get.size / 5 else 0
      }
    }
  }

  /**
   *
   * @return ( parameterTypeName, parameterTypeName ) => returnTypeName
   */
  override def toString = appendTo(new StringBuilder(128)).toString()
}
