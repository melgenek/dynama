package dynama.mapper.item.converter

import dynama.mapper.ItemConverter.MissingAttributeException
import dynama.mapper.{DynamoAttribute, ItemConverter}
import org.scalatest.{FlatSpec, Matchers}
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

class AnnotationItemConverterSpec extends FlatSpec with Matchers {

  case class SimpleClass(@DynamoAttribute("renamedField") field1: String, field2: String)

  val simpleConverter: ItemConverter[SimpleClass] = ItemConverter.converter[SimpleClass]

  "ItemConverter" should "convert case class to map" in {
    val test = SimpleClass("value1", "value2")

    val result = simpleConverter.toMap(test)

    result should be(Map(
      "renamedField" -> AttributeValue.builder().s("value1").build(),
      "field2" -> AttributeValue.builder().s("value2").build()
    ))
  }

  it should "read case class from map" in {
    val map = Map(
      "renamedField" -> AttributeValue.builder().s("value1").build(),
      "field2" -> AttributeValue.builder().s("value2").build()
    )

    val result = simpleConverter.fromMap(map)

    result should be(SimpleClass("value1", "value2"))
  }

  it should "throw exception when value is missing" in {
    val map = Map(
      "field1" -> AttributeValue.builder().s("value1").build(),
      "field2" -> AttributeValue.builder().s("value2").build()
    )

    a[MissingAttributeException] should be thrownBy {
      simpleConverter.fromMap(map)
    }
  }


}
