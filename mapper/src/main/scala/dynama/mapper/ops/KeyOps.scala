package dynama.mapper.ops

import dynama.mapper.{AttributeConverter, PartitionKey, ReflectUtil, SortKey}

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

abstract class SingleKeyOps[T, K: AttributeConverter] {

  val partitionKey: PartitionKey[K]

  protected def partitionAttribute(keySelector: T => K): PartitionKey[K] = macro KeyOps.partitionKey[T]

}

abstract class CompositeKeyOps[T, K: AttributeConverter, S: AttributeConverter] extends SingleKeyOps[T, K] {

  val sortKey: SortKey[S]

  protected def sortAttribute(keySelector: T => S): SortKey[S] = macro KeyOps.sortKey[T]

}

class KeyOps(val c: blackbox.Context) extends ReflectUtil {

  import c.universe._

  def partitionKey[A: WeakTypeTag](keySelector: Tree): Tree = {
    val rootType: Type = weakTypeOf[A]

    if (!isCaseClass(rootType))
      c.abort(c.enclosingPosition, s"Type $rootType is not a case class")

    val (keyName, keyType) = fieldWithConverter(keySelector, rootType)

    q"dynama.mapper.PartitionKey[$keyType]($keyName)"
  }


  def sortKey[A: WeakTypeTag](keySelector: Tree): Tree = {
    val rootType: Type = weakTypeOf[A]

    if (!isCaseClass(rootType))
      c.abort(c.enclosingPosition, s"Type $rootType is not a case class")

    val (keyName, keyType) = fieldWithConverter(keySelector, rootType)

    q"dynama.mapper.SortKey[$keyType]($keyName)"
  }

  private def fieldWithConverter(selector: Tree, rootType: Type): (String, c.universe.Type) = {
    val selectorFieldName: String = extractSelectorFieldName(selector).decodedName.toString
    val m = rootType.decls
      .collectFirst {
        case m: MethodSymbol if m.isCaseAccessor && m.name.decodedName.toString == selectorFieldName => m
      }
      .getOrElse {
        c.abort(c.enclosingPosition, s"'$selectorFieldName' is not an argument in the first primary constructor")
      }
    val methodType = m.info.resultType
    val keyName = attributeName(m)

    (keyName, methodType)
  }

  private def extractSelectorFieldName(selector: Tree): Name = {
    selector match {
      case q"(${vd: ValDef}) => ${idt: Ident}.${fieldName: Name} " if vd.name == idt.name =>
        fieldName
      case _ =>
        c.abort(c.enclosingPosition, s"Invalid selector '$selector'. Key selector can have only one level of nesting")
    }
  }

}
