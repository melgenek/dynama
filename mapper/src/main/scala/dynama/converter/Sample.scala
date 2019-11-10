package dynama.converter

import software.amazon.awssdk.services.dynamodb.model.AttributeValue

object Sample extends App {

  case class Nested(field3: Int)

  case class Example(field1: String, field2: Int, nested: Nested)

  implicit val NestedConverter = CompositeConverter[Nested](
    Attribute("field3", _.field3)
  )(Nested.apply)

  val ExampleConverter = CompositeConverter[Example](
    Attribute("name1", _.field1),
    Attribute("name2", _.field2),
    Attribute.flattened(_.nested)
  )(Example.apply)

  val encoded = ExampleConverter.encode(Example("123", 2, Nested(33)))
  println(encoded)

  val decoded = ExampleConverter.decode(Map(
    "name1" -> AttributeValue.builder().s("bla").build(),
    "name2" -> AttributeValue.builder().n("1").build(),
    "field3" -> AttributeValue.builder().n("123").build()
  ))
  println(decoded)


}


