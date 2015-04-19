package codesniffer.core


/**
 * Created by Bowen Cai on 4/13/2015.
 */
object MethodSignature {
  val empty = new MethodSignature("UNK", None, None, None)
}
case class MethodSignature(typeName: String, // why not ref javaparser.Type? javaparser.Type is a node and may cause mem leak
                           parameterTypes: Option[Array[String]],
                           annotations: Option[Array[String]],
                           throws: Option[Array[String]]
                            ) {


}
