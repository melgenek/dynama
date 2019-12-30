package dynama.mapper.converter

import dynama.converter.{Attribute, CompositeConverter}
import org.scalatest.{EitherValues, FlatSpec, Matchers}
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

import scala.collection.JavaConverters._

class OptionCompositeConverterSpec extends FlatSpec with Matchers with EitherValues {

  case class A(attr1: String, attr2: Option[Int], c: Option[C])

  case class C(attr3: String)

  implicit val CConverter: CompositeConverter[C] = CompositeConverter[C](
    Attribute("attr3", _.attr3)
  )(C.apply)

  val AConverter: CompositeConverter[A] = CompositeConverter[A](
    Attribute("attr1", _.attr1),
    Attribute("attr2", _.attr2),
    Attribute("c", _.c)
  )(A.apply)

  "CompositeConverter" should "convert nested optional attributes to map" in {
    val test = A("value1", Some(123), Some(C("value3")))

    val result = AConverter.encode(test)

    result should be(Map(
      "attr1" -> AttributeValue.builder().s("value1").build(),
      "attr2" -> AttributeValue.builder().n("123").build(),
      "c" -> AttributeValue.builder().m(Map(
        "attr3" -> AttributeValue.builder().s("value3").build(),
      ).asJava).build()
    ))
  }

  "CompositeConverter" should "convert nested optional attributes to map when they are none" in {
    val test = A("value1", None, None)

    val result = AConverter.encode(test)

    result should be(Map(
      "attr1" -> AttributeValue.builder().s("value1").build(),
      "attr2" -> AttributeValue.builder().nul(true).build(),
      "c" -> AttributeValue.builder().nul(true).build()
    ))
  }

  it should "read nested case class from map" in {
    val map = Map(
      "attr1" -> AttributeValue.builder().s("value1").build(),
      "attr2" -> AttributeValue.builder().n("123").build(),
      "c" -> AttributeValue.builder().m(Map(
        "attr3" -> AttributeValue.builder().s("value3").build(),
      ).asJava).build()
    )

    val result = AConverter.decode(map)

    result.right.value should be(A("value1", Some(123), Some(C("value3"))))
  }

  it should "read nested case class from map without optional values" in {
    val map = Map(
      "attr1" -> AttributeValue.builder().s("value1").build(),
    )

    val result = AConverter.decode(map)

    result.right.value should be(A("value1", None, None))
  }

  it should "read nested case class from map with null optional values" in {
    val map = Map(
      "attr1" -> AttributeValue.builder().s("value1").build(),
      "attr2" -> AttributeValue.builder().nul(true).build(),
      "c" -> AttributeValue.builder().nul(true).build()
    )

    val result = AConverter.decode(map)

    result.right.value should be(A("value1", None, None))
  }

}
