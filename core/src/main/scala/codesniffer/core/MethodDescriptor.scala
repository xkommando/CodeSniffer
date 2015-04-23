package codesniffer.core


/**
 * Created by Bowen Cai on 4/13/2015.
 */
object MethodDescriptor {
  val empty = new MethodDescriptor("UNK", None, None, None)
}
case class MethodDescriptor(typeName: String, // why not ref javaparser.Type? javaparser.Type is a node and may cause mem leak
                           parameterTypes: Option[Seq[String]],
                           annotations: Option[Seq[String]],
                           throws: Option[Seq[String]]
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
  /**
   *
   * @return ( parameterTypeName, parameterTypeName ) => returnTypeName
   */
  override def toString = appendTo(new StringBuilder(128)).toString()

}
