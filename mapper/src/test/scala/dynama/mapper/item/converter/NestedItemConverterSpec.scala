package dynama.mapper.item.converter

import dynama.mapper.ItemConverter.FlatRef
import dynama.mapper.{AttributeConverter, ItemConverter}
import org.scalatest.{FlatSpec, Matchers}
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

class NestedItemConverterSpec extends FlatSpec with Matchers {

  case class A(field1: String, b: FlatRef[B], c: C)

  case class B(field2: String, c: FlatRef[C])

  case class C(field3: String) {
    override def toString: String = s"C::$field3"
  }

  implicit val cConverter: AttributeConverter[C] = AttributeConverter.StringConverter.imap(
    "C::" + _.field3,
    v => C(v.replace("C::", ""))
  )
  val aConverter: ItemConverter[A] = ItemConverter.converter[A]


  "ItemConverter" should "convert nested case class to map" in {
    val test = A("value1", B("value2", C("value3")), C("value4"))

    val result = aConverter.toMap(test)

    result should be(Map(
      "field1" -> AttributeValue.builder().s("value1").build(),
      "field2" -> AttributeValue.builder().s("value2").build(),
      "field3" -> AttributeValue.builder().s("value3").build(),
      "c" -> AttributeValue.builder().s("C::value4").build()
    ))
  }

  it should "read nested case class from map" in {
    val map = Map(
      "field1" -> AttributeValue.builder().s("value1").build(),
      "field2" -> AttributeValue.builder().s("value2").build(),
      "field3" -> AttributeValue.builder().s("value3").build(),
      "c" -> AttributeValue.builder().s("C::value4").build()
    )

    val result = aConverter.fromMap(map)

    result should be(A("value1", B("value2", C("value3")), C("value4")))
  }

}
