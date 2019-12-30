package dynama.mapper.converter

import dynama.converter.{Attribute, CompositeConverter}
import org.scalatest.{EitherValues, FlatSpec, Matchers}
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

import scala.collection.JavaConverters._

class VariantCompositeConverterSpec extends FlatSpec with Matchers with EitherValues {

  sealed trait A
  case class B(attr1: String) extends A
  case class C(attr2: Int) extends A
  case class D(attr3: Boolean) extends A
  case class E(attr4: Long) extends A

  implicit val BConverter: CompositeConverter[B] = CompositeConverter[B](
    Attribute("attr1", _.attr1)
  )(B.apply)

  implicit val CConverter: CompositeConverter[C] = CompositeConverter[C](
    Attribute("attr2", _.attr2)
  )(C.apply)

  implicit val DConverter: CompositeConverter[D] = CompositeConverter[D](
    Attribute("attr3", _.attr3)
  )(D.apply)

  val AConverter: CompositeConverter[A] = CompositeConverter[A].chooseBy("aType", "c").options(
    "b" -> BConverter,
    "c" -> CConverter,
    "d" -> DConverter
  )

  "CompositeConverter" should "convert a variant class to map" in {
    val test = D(true)

    val result = AConverter.encode(test)

    result should be(Map(
      "aType" -> AttributeValue.builder().s("d").build(),
      "attr3" -> AttributeValue.builder().bool(true).build(),
    ))
  }

  it should "read a variant class from map" in {
    val map = Map(
      "aType" -> AttributeValue.builder().s("d").build(),
      "attr3" -> AttributeValue.builder().bool(true).build()
    )

    val result = AConverter.decode(map)

    result.right.value should be(D(true))
  }

  it should "read the default variant class from map when the discriminator field is absent" in {
    val map = Map(
      "attr2" -> AttributeValue.builder().n("123").build()
    )

    val result = AConverter.decode(map)

    result.right.value should be(C(123))
  }

  it should "not convert a variant class to map when the class converter is not defined as a variant" in {
    val test = E(321)

    intercept[IllegalArgumentException] {
      AConverter.encode(test)
    }
  }

}
