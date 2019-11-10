package dynama.converter

import software.amazon.awssdk.services.dynamodb.model.AttributeValue

import scala.util.Try


trait SimpleConverter[T] {
  def decode(attribute: AttributeValue): DecodingResult[T]

  def encode(value: T): EncodingResult[AttributeValue]
}

object SimpleConverter {

  private def decodeIfNotNull[T](attribute: AttributeValue)(decodedValue: => DecodingResult[T]): DecodingResult[T] = {
    if (attribute.nul()) Left(DecodingError("The attribute is null"))
    else decodedValue
  }

  implicit val IntConverter: SimpleConverter[Int] = new SimpleConverter[Int] {
    override def decode(attribute: AttributeValue): DecodingResult[Int] = {
      decodeIfNotNull(attribute) {
        Try(attribute.n().toInt)
          .toEither
          .left.map(DecodingError("The attribute is not an Int", _))
      }
    }

    override def encode(value: Int): EncodingResult[AttributeValue] = Right(AttributeValue.builder().n(value.toString).build())
  }

  implicit val StringConverter: SimpleConverter[String] = new SimpleConverter[String] {
    override def decode(attribute: AttributeValue): DecodingResult[String] = {
      decodeIfNotNull(attribute) {
        Right(attribute.s())
      }
    }

    override def encode(value: String): EncodingResult[AttributeValue] = Right(AttributeValue.builder().s(value).build())
  }

}

