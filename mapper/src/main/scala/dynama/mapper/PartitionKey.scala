package dynama.mapper

import dynama.mapper.Aliases.EvaluatedKey

case class PartitionKey[A: AttributeConverter : AttributeTypeConverter](name: String) {
  val converter: AttributeConverter[A] = implicitly[AttributeConverter[A]]
  val typeConverter: AttributeTypeConverter[A] = implicitly[AttributeTypeConverter[A]]
}

object PartitionKey {

  def evaluate[A](key: PartitionKey[A], value: A): EvaluatedKey = {
    (
      "#pkName = :pkValue",
      Map("#pkName" -> key.name),
      Map(":pkValue" -> key.converter.toAttribute(value))
    )
  }

}
