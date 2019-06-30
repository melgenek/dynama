package dynama.mapper

import dynama.mapper.ItemConverter.{FlatRef, InvalidAttributeException, MissingAttributeException}
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

import scala.language.experimental.macros
import scala.reflect.macros.blackbox
import scala.util.Failure


trait ItemConverter[T] {

  def toMap(t: T): Map[String, AttributeValue]

  def fromMap(map: Map[String, AttributeValue]): T

}

object ItemConverter {

  type FlatRef[A] = A

  def converter[T]: ItemConverter[T] = macro ItemConverterMacro.converter[T]

  sealed abstract class ItemConverterException(message: String, cause: Throwable) extends RuntimeException(message, cause)

  class MissingAttributeException(name: String, cause: NoSuchElementException) extends ItemConverterException(s"No attribute with name '$name' is present", cause)

  class InvalidAttributeException(name: String, cause: Throwable) extends ItemConverterException(s"Attribute with name '$name' has an invalid format", cause)

}

class ItemConverterMacro(val c: blackbox.Context) {

  import c.universe._

  private val DynamoAttributeConverterTypeSymbol: c.universe.TypeSymbol = typeOf[AttributeConverter[_]].typeSymbol.asType
  private val DynamoAttributeType = typeOf[DynamoAttribute]
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

  private def attributeName(m: c.universe.MethodSymbol): String = {
    m.accessed.annotations
      .find(_.tree.tpe =:= DynamoAttributeType)
      .map(_.tree.children.tail.head)
      .map { t =>
        util.Try(c.eval(c.Expr[String](t))) match {
          case scala.util.Success(name) => name
          case _ => c.abort(c.enclosingPosition, "Invalid annotation")
        }
      }
      .getOrElse(m.name.toTermName.decodedName.toString)
  }

  private def findImplicit(implicitType: Tree): Tree = {
    val converter = c.typecheck(q"_root_.scala.Predef.implicitly[$implicitType]", silent = true) match {
      case EmptyTree => c.abort(c.enclosingPosition, s"Unable to find implicit value of type $implicitType")
      case t => t
    }
    converter
  }

  private def isCaseClass(methodType: Type): Boolean = {
    val methodTypeSymbol = methodType.typeSymbol
    methodTypeSymbol.isClass && methodTypeSymbol.asClass.isCaseClass
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
