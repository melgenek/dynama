package dynama.mapper

case class PartitionKey[A: AttributeConverter](name: String) {
  val converter: AttributeConverter[A] = implicitly[AttributeConverter[A]]
}
