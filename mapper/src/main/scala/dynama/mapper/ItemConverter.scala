package dynama.mapper

import dynama.mapper.ItemConverter.FlatRef
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

import scala.language.experimental.macros
import scala.reflect.macros.blackbox
import scala.util.{Failure, Try}


trait ItemConverter[T] {

  def toMap(t: T): Map[String, AttributeValue]

  def fromMap(map: Map[String, AttributeValue]): T

}


class ItemConverterMacro(val c: blackbox.Context) {

  import c.universe._

  private val DynamoAttributeConverterTypeSymbol: c.universe.TypeSymbol = typeOf[AttributeConverter[_]].typeSymbol.asType
  private val DynamoAttributeType = typeOf[DynamoAttribute]
  private val FlatRefType: c.universe.Type = typeOf[FlatRef[_]]

  def converter[T: WeakTypeTag]: Expr[ItemConverter[T]] = {
    val rootType: Type = weakTypeOf[T]

    if (!isCaseClass(rootType.typeSymbol))
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
        val methodType = m.info.typeSymbol
        val fieldValue = q"$callPrefix.${m.name.toTermName}"

        if (isFlatRef(m.info) && isCaseClass(methodType)) classToMapEntries(m.info, fieldValue)
        else {
          val name = attributeName(m)
          val converter = findImplicit(tq"""$DynamoAttributeConverterTypeSymbol[$methodType]""")

          Seq(q"$name -> $converter.toAttribute($fieldValue)")
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
        val methodType = m.info.typeSymbol

        if (isFlatRef(m.info) && isCaseClass(methodType)) mapToClass(m.info)
        else {
          val name: String = attributeName(m)

          val converter = findImplicit(tq"""$DynamoAttributeConverterTypeSymbol[$methodType]""")
          //          q"$converter.fromAttribute(map($name))"
          q"dynama.mapper.ItemConverter.readAttribute(map, $name, $converter)"
        }
      }

    q"$companion(..$constructorArguments)"
  }

  private def attributeName(m: c.universe.MethodSymbol): String =
    m.accessed.annotations
      .find(_.tree.tpe =:= DynamoAttributeType)
      .map(_.tree.children.tail.head.toString)
      .getOrElse(m.name.toTermName.decodedName.toString)

  private def findImplicit(implicitType: Tree): Tree = {
    val converter = c.typecheck(q"_root_.scala.Predef.implicitly[$implicitType]", silent = true) match {
      case EmptyTree => c.abort(c.enclosingPosition, s"Unable to find implicit value of type $implicitType")
      case t => t
    }
    converter
  }

  private def isCaseClass(symbol: Symbol): Boolean =
    symbol.isClass && symbol.asClass.isCaseClass

  private def isFlatRef(methodType: Type): Boolean =
    methodType.resultType.typeConstructor =:= FlatRefType.typeConstructor


}

object ItemConverter {

  type FlatRef[A] = A

  def converter[T]: ItemConverter[T] = macro ItemConverterMacro.converter[T]

  private[mapper] def readAttribute[T](map: Map[String, AttributeValue],
                                       name: String,
                                       attributeConverter: AttributeConverter[T]): T = {
    Try(map(name))
      .map(attributeConverter.fromAttribute)
      .recoverWith {
        case e: NoSuchElementException => Failure(new MissingAttributeException(name, e))
        case e => Failure(new InvalidAttributeException(name, e))
      }
      .get
  }

  sealed abstract class ItemConverterException(message: String, cause: Throwable) extends RuntimeException(message, cause)

  class MissingAttributeException(name: String, cause: NoSuchElementException) extends ItemConverterException(s"No attribute with name '$name' is present", cause)

  class InvalidAttributeException(name: String, cause: Throwable) extends ItemConverterException(s"Attribute with name '$name' has an invalid format", cause)

}
