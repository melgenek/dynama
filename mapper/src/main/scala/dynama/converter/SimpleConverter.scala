package dynama.converter

import software.amazon.awssdk.services.dynamodb.model.AttributeValue

import scala.collection.JavaConverters._
import scala.util.Try

trait SimpleConverter[T] extends (String => CompositeConverter[T]) {
  final def imap[A](contramap: A => T, map: T => A): SimpleConverter[A] =
    name => new CompositeConverter[A] {
      override def decode(m: Map[String, AttributeValue]): DecodingResult[A] = {
        SimpleConverter.this.apply(name).decode(m).map(map)
      }

      override def encode(value: A): Map[String, AttributeValue] = {
        SimpleConverter.this.apply(name).encode(contramap(value))
      }
    }
}

object SimpleConverter {

  def apply[A](implicit c: SimpleConverter[A]): SimpleConverter[A] = c

  def create[A](decodeF: AttributeValue => DecodingResult[A],
                encodeF: A => AttributeValue): SimpleConverter[A] = name => {
    new CompositeConverter[A] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[A] = {
        val attribute = map.getOrElse(name, NulAttributeValue)
        decodeF(attribute)
      }

      override def encode(value: A): Map[String, AttributeValue] = {
        Map(name -> encodeF(value))
      }
    }
  }

  private final val NulAttributeValue = AttributeValue.builder().nul(true).build()

  private def numberConverter[T: Numeric](fromString: String => T, name: String): SimpleConverter[T] = create[T](
    attribute => Try(fromString(attribute.n()))
      .toEither
      .left.map(DecodingError(s"The attribute value '${attribute.n()}' is not $name", _)),
    value => AttributeValue.builder().n(value.toString).build()
  )

  implicit val IntConverter: SimpleConverter[Int] = numberConverter(_.toInt, "an Int")
  implicit val LongConverter: SimpleConverter[Long] = numberConverter(_.toLong, "a Long")
  implicit val ShortConverter: SimpleConverter[Short] = numberConverter(_.toShort, "a Short")
  implicit val ByteConverter: SimpleConverter[Byte] = numberConverter(_.toByte, "a Byte")
  implicit val FloatConverter: SimpleConverter[Float] = numberConverter(_.toFloat, "a Float")
  implicit val DoubleConverter: SimpleConverter[Double] = numberConverter(_.toDouble, "a Double")
  implicit val BigDecimalConverter: SimpleConverter[BigDecimal] = numberConverter(BigDecimal(_), "a Double")

  implicit val CharConverter: SimpleConverter[Char] = create(
    attribute => attribute.s().headOption
      .map(Right(_))
      .getOrElse(Left(DecodingError("Empty string is not a Char"))),
    value => AttributeValue.builder().s(value.toString).build()
  )

  implicit val StringConverter: SimpleConverter[String] = create(
    attribute => Right(attribute.s()),
    value => AttributeValue.builder().s(value).build()
  )

  implicit val BooleanConverter: SimpleConverter[Boolean] = create(
    attribute => Right(attribute.bool()),
    value => AttributeValue.builder().bool(value).build()
  )

  implicit def optionConverter[T](implicit converter: SimpleConverter[T]): SimpleConverter[Option[T]] =
    name => {
      new CompositeConverter[Option[T]] {
        override def decode(map: Map[String, AttributeValue]): DecodingResult[Option[T]] = {
          val attribute = map.getOrElse(name, NulAttributeValue)
          if (!attribute.nul()) converter(name).decode(map).map(Some(_))
          else Right(Option.empty[T])
        }

        override def encode(value: Option[T]): Map[String, AttributeValue] = {
          value.map(converter(name).encode).getOrElse(Map(name -> NulAttributeValue))
        }
      }
    }

  implicit def mapConverter[T](implicit converter: CompositeConverter[T]): SimpleConverter[T] = create(
    attribute => converter.decode(attribute.m().asScala.toMap),
    value => AttributeValue.builder().m(converter.encode(value).asJava).build()
  )

}

