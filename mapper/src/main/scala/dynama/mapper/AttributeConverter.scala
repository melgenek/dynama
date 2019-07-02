package dynama.mapper

import software.amazon.awssdk.services.dynamodb.model.AttributeValue

trait AttributeConverter[T] {
  def toAttribute(t: T): AttributeValue

  def fromAttribute(value: AttributeValue): T

  final def imap[A](contramap: A => T, map: T => A): AttributeConverter[A] = new AttributeConverter[A] {
    override def toAttribute(t: A): AttributeValue = AttributeConverter.this.toAttribute(contramap(t))

    override def fromAttribute(value: AttributeValue): A = map(AttributeConverter.this.fromAttribute(value))
  }
}

object AttributeConverter {

  implicit val IntConverter: AttributeConverter[Int] = new AttributeConverter[Int] {
    override def toAttribute(t: Int): AttributeValue = AttributeValue.builder().n(t.toString).build()

    override def fromAttribute(value: AttributeValue): Int = value.n().toInt
  }

  implicit val ShortConverter: AttributeConverter[Short] = new AttributeConverter[Short] {
    override def toAttribute(t: Short): AttributeValue = AttributeValue.builder().n(t.toString).build()

    override def fromAttribute(value: AttributeValue): Short = value.n().toShort
  }

  implicit val LongConverter: AttributeConverter[Long] = new AttributeConverter[Long] {
    override def toAttribute(t: Long): AttributeValue = AttributeValue.builder().n(t.toString).build()

    override def fromAttribute(value: AttributeValue): Long = value.n().toLong
  }

  implicit val DoubleConverter: AttributeConverter[Double] = new AttributeConverter[Double] {
    override def toAttribute(t: Double): AttributeValue = AttributeValue.builder().n(t.toString).build()

    override def fromAttribute(value: AttributeValue): Double = value.n().toDouble
  }

  implicit val FloatConverter: AttributeConverter[Float] = new AttributeConverter[Float] {
    override def toAttribute(t: Float): AttributeValue = AttributeValue.builder().n(t.toString).build()

    override def fromAttribute(value: AttributeValue): Float = value.n().toFloat
  }

  implicit val ByteConverter: AttributeConverter[Byte] = new AttributeConverter[Byte] {
    override def toAttribute(t: Byte): AttributeValue = AttributeValue.builder().n(t.toString).build()

    override def fromAttribute(value: AttributeValue): Byte = value.n().toByte
  }

  implicit val CharConverter: AttributeConverter[Char] = new AttributeConverter[Char] {
    override def toAttribute(t: Char): AttributeValue = AttributeValue.builder().s(t.toString).build()

    override def fromAttribute(value: AttributeValue): Char = value.s().head
  }

  implicit val BooleanConverter: AttributeConverter[Boolean] = new AttributeConverter[Boolean] {
    override def toAttribute(t: Boolean): AttributeValue = AttributeValue.builder().bool(t).build()

    override def fromAttribute(value: AttributeValue): Boolean = value.bool()
  }

  implicit val StringConverter: AttributeConverter[String] = new AttributeConverter[String] {
    override def toAttribute(t: String): AttributeValue = AttributeValue.builder().s(t).build()

    override def fromAttribute(value: AttributeValue): String = value.s()
  }

  implicit def optionConverter[A](implicit c: AttributeConverter[A]): AttributeConverter[Option[A]] =
    new AttributeConverter[Option[A]] {
      override def toAttribute(valueOpt: Option[A]): AttributeValue =
        valueOpt
          .map(value => c.toAttribute(value))
          .getOrElse(AttributeValue.builder().nul(true).build())

      override def fromAttribute(value: AttributeValue): Option[A] =
        if (value.nul()) Option.empty
        else Some(c.fromAttribute(value))
    }

}
