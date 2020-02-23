package dynama.converter

import software.amazon.awssdk.services.dynamodb.model.AttributeValue

private[dynama] sealed trait Attribute[T, A] {
  val get: T => A
  val converter: CompositeConverter[A]
}

object Attribute {
  private final val NulAttributeValue = AttributeValue.builder().nul(true).build()

  def apply[T, A](name: String, getFromCaseClass: T => A)
                 (implicit simpleConverter: SimpleConverter[A]): Attribute[T, A] = new Attribute[T, A] {
    override val get: T => A = getFromCaseClass
    override val converter: CompositeConverter[A] = new CompositeConverter[A] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[A] = {
        simpleConverter.decode(map.getOrElse(name, NulAttributeValue))
      }

      override def encode(value: A): Map[String, AttributeValue] = {
        Map(name -> simpleConverter.encode(value))
      }
    }
  }

  def flat[T, A](getFromCaseClass: T => A)
                (implicit compositeConverter: CompositeConverter[A]): Attribute[T, A] = new Attribute[T, A] {
    override val get: T => A = getFromCaseClass
    override val converter: CompositeConverter[A] = compositeConverter
  }
}
