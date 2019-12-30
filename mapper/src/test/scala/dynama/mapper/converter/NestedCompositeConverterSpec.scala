package dynama.mapper.converter

import dynama.converter.{Attribute, CompositeConverter}
import org.scalatest.{EitherValues, FlatSpec, Matchers}
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

import scala.collection.JavaConverters._

class NestedCompositeConverterSpec extends FlatSpec with Matchers with EitherValues {

  case class A(attr1: String, b: B, c: C)

  case class B(attr2: String)

  case class C(attr3: String)

  implicit val CConverter: CompositeConverter[C] = CompositeConverter[C](
    Attribute("attr3", _.attr3)
  )(C.apply)

  implicit val BConverter: CompositeConverter[B] = CompositeConverter[B](
    Attribute("attr2", _.attr2)
  )(B.apply)

  val AConverter: CompositeConverter[A] = CompositeConverter[A](
    Attribute("attr1", _.attr1),
    Attribute.flat(_.b),
    Attribute("c", _.c)
  )(A.apply)

  "CompositeConverter" should "encode a nested case classes to map" in {
    val test = A("value1", B("value2"), C("value3"))

    val result = AConverter.encode(test)

    result should be(Map(
      "attr1" -> AttributeValue.builder().s("value1").build(),
      "attr2" -> AttributeValue.builder().s("value2").build(),
      "c" -> AttributeValue.builder().m(Map(
        "attr3" -> AttributeValue.builder().s("value3").build(),
      ).asJava).build()
    ))
  }

  it should "decode a nested case class from map" in {
    val map = Map(
      "attr1" -> AttributeValue.builder().s("value1").build(),
      "attr2" -> AttributeValue.builder().s("value2").build(),
      "c" -> AttributeValue.builder().m(Map(
        "attr3" -> AttributeValue.builder().s("value3").build(),
      ).asJava).build()
    )

    val result = AConverter.decode(map)

    result.right.value should be(A("value1", B("value2"), C("value3")))
  }

}
