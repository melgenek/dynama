package dynama.mapper

import dynama.mapper.ItemConverter.{FlatRef, InvalidAttributeException, MissingAttributeException}
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

import scala.language.experimental.macros
import scala.reflect.macros.blackbox
import scala.util.Failure

trait ItemWriter[T] {
  def toMap(t: T): Map[String, AttributeValue]
}

trait ItemReader[T] {
  def fromMap(map: Map[String, AttributeValue]): T
}

trait ItemConverter[T] extends ItemWriter[T] with ItemReader[T]

object ItemConverter {

  type FlatRef[A] = A

  def converter[T]: ItemConverter[T] = macro ItemConverterMacro.converter[T]

  sealed abstract class ItemConverterException(message: String, cause: Throwable) extends RuntimeException(message, cause)

  class MissingAttributeException(name: String, cause: NoSuchElementException) extends ItemConverterException(s"No attribute with name '$name' is present", cause)

  class InvalidAttributeException(name: String, cause: Throwable) extends ItemConverterException(s"Attribute with name '$name' has an invalid format", cause)

}

class ItemConverterMacro(val c: blackbox.Context) extends ReflectUtil {

  import c.universe._

  private val FlatRefType: c.universe.Type = typeOf[FlatRef[_]]

  def converter[T: WeakTypeTag]: Expr[ItemConverter[T]] = {
    val rootType: Type = weakTypeOf[T]

    if (!isCaseClass(rootType))
      c.abort(c.enclosingPosition, s"Type $rootType is not a case class")

    val toMapEntries: Seq[Tree] = classToMapEntries(rootType, q"root")
    if (toMapEntries.isEmpty)
      c.abort(c.enclosingPosition, s"Primary constructor of class $rootType contains no fields")

    val fromMapValue = mapToClass(rootType)

    c.Expr[ItemConverter[T]] {
      q"""
      import software.amazon.awssdk.services.dynamodb.model.AttributeValue

      new ItemConverter[$rootType] {
        def toMap(root: $rootType): Map[String, AttributeValue] = Map(..$toMapEntries)
        def fromMap(map: Map[String, AttributeValue]): $rootType = $fromMapValue
      }
    """
    }

  }

  private def classToMapEntries(caseClassType: Type, callPrefix: Tree): Seq[Tree] = {
    caseClassType.decls
      .collect {
        case m: MethodSymbol if m.isCaseAccessor => m
      }
      .flatMap { m =>
        val methodType = m.info.resultType
        val fieldValue = q"$callPrefix.${m.name.toTermName}"

        if (isFlatRef(methodType) && isCaseClass(methodType)) classToMapEntries(methodType, fieldValue)
        else {
          val name = attributeName(m)
          val attributeConverter = findImplicit(tq"""$DynamoAttributeConverterTypeSymbol[$methodType]""")

          Seq(q"$name -> $attributeConverter.toAttribute($fieldValue)")
        }
      }
      .toSeq
  }

  private def mapToClass(caseClassType: Type): Tree = {
    val companion: Symbol = caseClassType.typeSymbol.companion
    val constructorArguments: Iterable[Tree] = caseClassType.decls
      .collect {
        case m: MethodSymbol if m.isCaseAccessor => m
      }
      .map { m =>
        val methodType = m.info.resultType

        if (isFlatRef(methodType) && isCaseClass(methodType)) mapToClass(methodType)
        else {
          val name: String = attributeName(m)

          val converter = findImplicit(tq"""$DynamoAttributeConverterTypeSymbol[$methodType]""")
          q"dynama.mapper.ItemConverterUtil.readAttribute(map, $name, $converter)"
        }
      }

    q"$companion(..$constructorArguments)"
  }

  private def isFlatRef(methodType: Type): Boolean =
    methodType.resultType.typeConstructor =:= FlatRefType.typeConstructor

}

object ItemConverterUtil {

  def readAttribute[T](map: Map[String, AttributeValue],
                       name: String,
                       attributeConverter: AttributeConverter[T]): T = {
    util.Try(map(name))
      .map(attributeConverter.fromAttribute)
      .recoverWith {
        case e: NoSuchElementException => Failure(new MissingAttributeException(name, e))
        case e => Failure(new InvalidAttributeException(name, e))
      }
      .get
  }

}
