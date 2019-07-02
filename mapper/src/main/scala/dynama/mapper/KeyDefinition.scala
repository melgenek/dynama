package dynama.mapper

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

trait KeyDefinition[T]

object KeyDefinition {

  case class PartitionKeyDefinition[A, K](partitionKeyName: String, partitionKeyConverter: AttributeConverter[K]) extends KeyDefinition[A]

  case class CompositeKeyDefinition[A, K, S](partitionKeyName: String, partitionKeyConverter: AttributeConverter[K],
                                             sortKeyName: String, sortKeyConverter: AttributeConverter[S]) extends KeyDefinition[A]

  def partitionKey[A](partitionKeySelector: A => Any): KeyDefinition[A] = macro KeyDefinitionMacro.partitionKey[A]

  def compositeKey[A](partitionKeySelector: A => Any, sortKeySelector: A => Any): KeyDefinition[A] = macro KeyDefinitionMacro.compositeKey[A]

}

class KeyDefinitionMacro(val c: blackbox.Context) extends ReflectUtil {

  import c.universe._

  def partitionKey[A: WeakTypeTag](partitionKeySelector: Tree): Tree = {
    val rootType: Type = weakTypeOf[A]

    if (!isCaseClass(rootType))
      c.abort(c.enclosingPosition, s"Type $rootType is not a case class")

    val (partitionKey, partitionKeyType, partitionKeyAttributeConverter) = fieldWithConverter(partitionKeySelector, rootType)

    q"dynama.mapper.KeyDefinition.PartitionKeyDefinition[$rootType, $partitionKeyType]($partitionKey, $partitionKeyAttributeConverter)"
  }


  def compositeKey[A: WeakTypeTag](partitionKeySelector: Tree, sortKeySelector: Tree): Tree = {
    val rootType: Type = weakTypeOf[A]

    if (!isCaseClass(rootType))
      c.abort(c.enclosingPosition, s"Type $rootType is not a case class")

    val (partitionKey, partitionKeyType, partitionKeyAttributeConverter) = fieldWithConverter(partitionKeySelector, rootType)
    val (sortKey, sortKeyType, sortKeyAttributeConverter) = fieldWithConverter(sortKeySelector, rootType)

    q"dynama.mapper.KeyDefinition.CompositeKeyDefinition[$rootType, $partitionKeyType, $sortKeyType]($partitionKey, $partitionKeyAttributeConverter, $sortKey, $sortKeyAttributeConverter)"
  }

  private def fieldWithConverter(selector: Tree, rootType: Type): (String, c.universe.Type, c.universe.Tree) = {
    val selectorFieldName: String = extractSelectorFieldName(selector).decodedName.toString
    val m = rootType.decls
      .collectFirst {
        case m: MethodSymbol if m.isCaseAccessor && m.name.decodedName.toString == selectorFieldName => m
      }
      .getOrElse {
        c.abort(c.enclosingPosition, s"'$selectorFieldName' is not a parameter in the first primary constructor")
      }
    val methodType = m.info.resultType
    val keyName = attributeName(m)
    val attributeConverter = findImplicit(tq"""$DynamoAttributeConverterTypeSymbol[$methodType]""")

    (keyName, methodType, attributeConverter)
  }

  private def extractSelectorFieldName(selector: Tree): Name = {
    selector match {
      case q"(${vd: ValDef}) => ${idt: Ident}.${fieldName: Name}" if vd.name == idt.name =>
        fieldName
      case _ =>
        c.abort(c.enclosingPosition, s"Invalid selector '$selector'.Key selector can have only one level of nesting")
    }
  }


}


