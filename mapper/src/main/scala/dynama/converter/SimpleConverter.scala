package dynama.converter

import software.amazon.awssdk.services.dynamodb.model.AttributeValue

import scala.collection.JavaConverters._
import scala.util.Try

trait SimpleConverter[T] {
  def decode(attribute: AttributeValue): DecodingResult[T]

  def encode(value: T): AttributeValue

  final def imap[A](contramap: A => T, map: T => A): SimpleConverter[A] = new SimpleConverter[A] {
    override def decode(value: AttributeValue): DecodingResult[A] = SimpleConverter.this.decode(value).map(map)

    override def encode(t: A): AttributeValue = SimpleConverter.this.encode(contramap(t))
  }
}

object SimpleConverter {

  def apply[A](implicit c: SimpleConverter[A]): SimpleConverter[A] = c

  private def numberConverter[T: Numeric](fromString: String => T, name: String): SimpleConverter[T] = new SimpleConverter[T] {
    override def decode(attribute: AttributeValue): DecodingResult[T] =
      Try(fromString(attribute.n()))
        .toEither
        .left.map(DecodingError(s"The attribute value '${attribute.n()}' is not $name", _))

    override def encode(value: T): AttributeValue =
      AttributeValue.builder().n(value.toString).build()
  }

  implicit val IntConverter: SimpleConverter[Int] = numberConverter(_.toInt, "an Int")
  implicit val LongConverter: SimpleConverter[Long] = numberConverter(_.toLong, "a Long")
  implicit val ShortConverter: SimpleConverter[Short] = numberConverter(_.toShort, "a Short")
  implicit val ByteConverter: SimpleConverter[Byte] = numberConverter(_.toByte, "a Byte")
  implicit val FloatConverter: SimpleConverter[Float] = numberConverter(_.toFloat, "a Float")
  implicit val DoubleConverter: SimpleConverter[Double] = numberConverter(_.toDouble, "a Double")
  implicit val BigDecimalConverter: SimpleConverter[BigDecimal] = numberConverter(BigDecimal(_), "a Double")

  implicit val CharConverter: SimpleConverter[Char] = new SimpleConverter[Char] {
    override def decode(attribute: AttributeValue): DecodingResult[Char] =
      attribute.s().headOption
        .map(Right(_))
        .getOrElse(Left(DecodingError("Empty string is not a Char")))

    override def encode(value: Char): AttributeValue =
      AttributeValue.builder().s(value.toString).build()
  }

  implicit val StringConverter: SimpleConverter[String] = new SimpleConverter[String] {
    override def decode(attribute: AttributeValue): DecodingResult[String] = {
      Right(attribute.s())
    }

    override def encode(value: String): AttributeValue =
      AttributeValue.builder().s(value).build()
  }

  implicit val BooleanConverter: SimpleConverter[Boolean] = new SimpleConverter[Boolean] {
    override def decode(attribute: AttributeValue): DecodingResult[Boolean] = {
      Right(attribute.bool())
    }

    override def encode(value: Boolean): AttributeValue =
      AttributeValue.builder().bool(value).build()
  }

  implicit def optionConverter[T](implicit converter: SimpleConverter[T]): SimpleConverter[Option[T]] = new SimpleConverter[Option[T]] {
    override def decode(attribute: AttributeValue): DecodingResult[Option[T]] =
      Option(attribute)
        .filterNot(_.nul())
        .map(converter.decode)
        .map(_.map(Some(_)))
        .getOrElse(Right(Option.empty[T]))

    override def encode(value: Option[T]): AttributeValue =
      value.map(converter.encode).getOrElse(NulAttributeValue)
  }

  implicit def mapConverter[T](implicit converter: CompositeConverter[T]): SimpleConverter[T] = new SimpleConverter[T] {
    override def decode(attribute: AttributeValue): DecodingResult[T] =
      converter.decode(attribute.m().asScala.toMap)

    override def encode(value: T): AttributeValue =
      AttributeValue.builder().m(converter.encode(value).asJava).build()
  }

}

