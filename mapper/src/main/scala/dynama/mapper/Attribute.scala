package dynama.mapper

case class Attribute[A: AttributeConverter](name: String) {
  val converter: AttributeConverter[A] = implicitly[AttributeConverter[A]]
}
