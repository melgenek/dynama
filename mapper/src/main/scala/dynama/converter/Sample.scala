package dynama.converter

import software.amazon.awssdk.services.dynamodb.model.AttributeValue

import scala.collection.JavaConverters._

object Sample extends App {

  sealed trait Auth

  object Auth {

    case class Error(reason: Int) extends Auth

    case class User(id: Int, name: String) extends Auth

  }

  case class Nested(field3: Int)

  case class Example(field1: String, nested: Option[Nested], str: Option[String], auth: Auth = Auth.Error(222))

  val ErrorConverter = CompositeConverter[Auth.Error](
    Attribute("reason", _.reason)
  )(Auth.Error.apply)

  val UserConverter = CompositeConverter[Auth.User](
    Attribute("id", _.id),
    Attribute("name", _.name)
  )(Auth.User.apply)

  implicit val AuthConverter = CompositeConverter[Auth].chooseBy("authType", "error").options(
    "error" -> ErrorConverter,
    "user" -> UserConverter
  )

  implicit val NestedConverter = CompositeConverter[Nested](
    Attribute("field3", _.field3)
  )(Nested.apply)

  val ExampleConverter = CompositeConverter[Example](
    Attribute("name1", _.field1),
    Attribute("nested", _.nested),
    Attribute("str", _.str),
    Attribute.flat(_.auth)
  )(Example.apply)

  val encoded = ExampleConverter.encode(Example("123", Some(Nested(33)), Some("blabla")))
  println(encoded)

  val encoded2 = ExampleConverter.encode(Example("123", Some(Nested(33)), None))
  println(encoded2)

  val decoded = ExampleConverter.decode(Map(
    "name1" -> AttributeValue.builder().nul(true).build(),
    "field3" -> AttributeValue.builder().n("123").build(),
    "reason" -> AttributeValue.builder().n("222").build()
  ))
  println(decoded)

  val decoded2 = ExampleConverter.decode(Map(
    "name1" -> AttributeValue.builder().s("bla").build(),
    "str" -> AttributeValue.builder().nul(true).build(),
    "field3" -> AttributeValue.builder().n("adf").build(),
    "reason" -> AttributeValue.builder().n("222").build()
  ))
  println(decoded2)

  val decoded3 = ExampleConverter.decode(Map(
    "name1" -> AttributeValue.builder().s("bla").build(),
    "str" -> AttributeValue.builder().s("value").build(),
    "nested" -> AttributeValue.builder().m(Map(
      "field3" -> AttributeValue.builder().n("123").build()
    ).asJava).build(),
    "id" -> AttributeValue.builder().n("123123").build(),
    "name" -> AttributeValue.builder().s("mein name").build(),
    "authType" -> AttributeValue.builder().s("user").build()
  ))
  println(decoded3)

}
