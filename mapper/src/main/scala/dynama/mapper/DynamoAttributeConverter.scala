package dynama.mapper

import software.amazon.awssdk.services.dynamodb.model.AttributeValue

trait DynamoAttributeConverter[T] {
  def toAttribute(t: T): AttributeValue

  def fromAttribute(value: AttributeValue): T

  final def imap[A](contramap: A => T, map: T => A): DynamoAttributeConverter[A] = new DynamoAttributeConverter[A] {
    override def toAttribute(t: A): AttributeValue = DynamoAttributeConverter.this.toAttribute(contramap(t))

    override def fromAttribute(value: AttributeValue): A = map(DynamoAttributeConverter.this.fromAttribute(value))
  }
}

object DynamoAttributeConverter {

  implicit val IntConverter: DynamoAttributeConverter[Int] = new DynamoAttributeConverter[Int] {
    override def toAttribute(t: Int): AttributeValue = AttributeValue.builder().n(t.toString).build()

    override def fromAttribute(value: AttributeValue): Int = value.n().toInt
  }

  implicit val DoubleConverter: DynamoAttributeConverter[Double] = new DynamoAttributeConverter[Double] {
    override def toAttribute(t: Double): AttributeValue = AttributeValue.builder().n(t.toString).build()

    override def fromAttribute(value: AttributeValue): Double = value.n().toDouble
  }

  implicit val StringConverter: DynamoAttributeConverter[String] = new DynamoAttributeConverter[String] {
    override def toAttribute(t: String): AttributeValue = AttributeValue.builder().s(t.toString).build()

    override def fromAttribute(value: AttributeValue): String = value.s()
  }

}
