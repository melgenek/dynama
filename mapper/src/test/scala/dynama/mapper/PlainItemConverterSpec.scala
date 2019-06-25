package dynama.mapper

import dynama.mapper.ItemConverter.MissingAttributeException
import org.scalatest.{FlatSpec, Matchers}
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

class PlainItemConverterSpec extends FlatSpec with Matchers {

  case class SimpleClass(field1: String, field2: String)

  val simpleConverter: ItemConverter[SimpleClass] = ItemConverter.converter[SimpleClass]

  case class ClassWithUnusualNames(`the-field-1`: String,
                                   `type`: String,
                                   `line1\nline2`: String,
                                   `line1\\nline2`: String)

  val unusualNamesConverter: ItemConverter[ClassWithUnusualNames] = ItemConverter.converter[ClassWithUnusualNames]

  "ItemConverter" should "convert case class to map" in {
    val test = SimpleClass("value1", "value2")

    val result = simpleConverter.toMap(test)

    result should be(Map(
      "field1" -> AttributeValue.builder().s("value1").build(),
      "field2" -> AttributeValue.builder().s("value2").build()
    ))
  }

  it should "read case class from map" in {
    val map = Map(
      "field1" -> AttributeValue.builder().s("value1").build(),
      "field2" -> AttributeValue.builder().s("value2").build()
    )

    val result = simpleConverter.fromMap(map)

    result should be(SimpleClass("value1", "value2"))
  }

  it should "convert class with unusual names to map" in {
    val test = ClassWithUnusualNames("value1", """value2""", "cool\ncool", "cool\\ncool")

    val result = unusualNamesConverter.toMap(test)

    result should be(Map(
      "the-field-1" -> AttributeValue.builder().s("value1").build(),
      "type" -> AttributeValue.builder().s("value2").build(),
      "line1\nline2" -> AttributeValue.builder().s("cool\ncool").build(),
      "line1\\nline2" -> AttributeValue.builder().s("cool\\ncool").build()
    ))
  }

  it should "read class with unusual names from map" in {
    val map = Map(
      "the-field-1" -> AttributeValue.builder().s("value1").build(),
      "type" -> AttributeValue.builder().s("value2").build(),
      "line1\nline2" -> AttributeValue.builder().s("cool\ncool").build(),
      "line1\\nline2" -> AttributeValue.builder().s("cool\\ncool").build()
    )

    val result = unusualNamesConverter.fromMap(map)

    result should be(ClassWithUnusualNames("value1", """value2""", "cool\ncool", "cool\\ncool"))
  }

  it should "throw exception when value is missing" in {
    val map = Map("field1" -> AttributeValue.builder().s("value1").build())

    a[MissingAttributeException] should be thrownBy {
      simpleConverter.fromMap(map)
    }
  }

}
