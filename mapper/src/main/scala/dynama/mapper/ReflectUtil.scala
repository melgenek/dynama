package dynama.mapper

import scala.reflect.macros.blackbox

trait ReflectUtil {

  val c: blackbox.Context

  import c.universe._

  protected val DynamoAttributeConverterTypeSymbol: c.universe.TypeSymbol = typeOf[AttributeConverter[_]].typeSymbol.asType
  protected val DynamoAttributeType: c.universe.Type = typeOf[DynamoAttribute]

  protected def isCaseClass(methodType: Type): Boolean = {
    val methodTypeSymbol = methodType.typeSymbol
    methodTypeSymbol.isClass && methodTypeSymbol.asClass.isCaseClass
  }

  protected def findImplicit(implicitType: Tree): Tree = {
    val converter = c.typecheck(q"_root_.scala.Predef.implicitly[$implicitType]", silent = true) match {
      case EmptyTree => c.abort(c.enclosingPosition, s"Unable to find implicit value of type $implicitType")
      case t => t
    }
    converter
  }

  protected def attributeName(m: c.universe.MethodSymbol): String = {
    m.accessed.annotations
      .find(_.tree.tpe =:= DynamoAttributeType)
      .map(_.tree.children.tail.head)
      .map { t =>
        util.Try(c.eval(c.Expr[String](t))) match {
          case scala.util.Success(name) => name
          case _ => c.abort(c.enclosingPosition, s"Invalid DynamoAttribute annotation '$t'")
        }
      }
      .getOrElse(m.name.toTermName.decodedName.toString)
  }

}
