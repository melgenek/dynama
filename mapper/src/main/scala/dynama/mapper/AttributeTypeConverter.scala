package dynama.mapper

import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType

trait AttributeTypeConverter[T] {
  def toScalarAttributeType: ScalarAttributeType
}

object AttributeTypeConverter {

  def converter[T](scalarAttributeType: ScalarAttributeType): AttributeTypeConverter[T] = new AttributeTypeConverter[T] {
    override def toScalarAttributeType = scalarAttributeType
  }

  implicit val IntAttributeTypeConverter: AttributeTypeConverter[Int] = converter(ScalarAttributeType.N)

  implicit val LongAttributeTypeConverter: AttributeTypeConverter[Long] = converter(ScalarAttributeType.N)

  implicit val DoubleAttributeTypeConverter: AttributeTypeConverter[Double] = converter(ScalarAttributeType.N)

  implicit val ShortAttributeTypeConverter: AttributeTypeConverter[Short] = converter(ScalarAttributeType.N)

  implicit val FloatAttributeTypeConverter: AttributeTypeConverter[Float] = converter(ScalarAttributeType.N)

  implicit val StringAttributeTypeConverter: AttributeTypeConverter[String] = converter(ScalarAttributeType.S)

  implicit val CharAttributeTypeConverter: AttributeTypeConverter[Char] = converter(ScalarAttributeType.S)

}
