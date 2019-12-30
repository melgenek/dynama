package dynama.converter

import software.amazon.awssdk.services.dynamodb.model.AttributeValue

import scala.util.Try

import scala.collection.JavaConverters._

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

  implicit val IntConverter: SimpleConverter[Int] = new SimpleConverter[Int] {
    override def decode(attribute: AttributeValue): DecodingResult[Int] = {
      Try(attribute.n().toInt)
        .toEither
        .left.map(DecodingError(s"The attribute value '${attribute.n()}' is not an Int", _))
    }

    override def encode(value: Int): AttributeValue = {
      AttributeValue.builder().n(value.toString).build()
    }
  }

  implicit val StringConverter: SimpleConverter[String] = new SimpleConverter[String] {
    override def decode(attribute: AttributeValue): DecodingResult[String] = {
      Right(attribute.s())
    }

    override def encode(value: String): AttributeValue =
      AttributeValue.builder().s(value).build()
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

