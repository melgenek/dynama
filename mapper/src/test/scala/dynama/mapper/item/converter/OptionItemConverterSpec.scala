package dynama.mapper.item.converter

import dynama.mapper.ItemConverter
import dynama.mapper.ItemConverter.InvalidAttributeException
import org.scalatest.{FlatSpec, Matchers}
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

class OptionItemConverterSpec extends FlatSpec with Matchers {

  case class IntClass(value: Option[Int])

  case class BooleanClass(value: Option[Boolean])

  val intConverter: ItemConverter[IntClass] = ItemConverter.converter[IntClass]
  val booleanConverter: ItemConverter[BooleanClass] = ItemConverter.converter[BooleanClass]

  "ItemConverter" should "converter class with optional int into map" in {
    val test = IntClass(Some(Int.MinValue))

    val result = intConverter.toMap(test)

    result should be(Map(
      "value" -> AttributeValue.builder().n("-2147483648").build()
    ))
  }

  it should "converter class with empty optional int into map" in {
    val test = IntClass(None)

    val result = intConverter.toMap(test)

    result should be(Map(
      "value" -> AttributeValue.builder().nul(true).build()
    ))
  }

  it should "read optional int class from map" in {
    val map = Map("value" -> AttributeValue.builder().n("-2147483648").build())

    val result = intConverter.fromMap(map)

    result should be(IntClass(Some(Int.MinValue)))
  }

  it should "read optional int class from map when value is empty" in {
    val map = Map("value" -> AttributeValue.builder().nul(true).build())

    val result = intConverter.fromMap(map)

    result should be(IntClass(None))
  }

  it should "throw exception when read optional int class from map when value is invalid" in {
    val map = Map("value" -> AttributeValue.builder().n("not_an_int").build())

    a[InvalidAttributeException] should be thrownBy {
      intConverter.fromMap(map)
    }
  }

  it should "converter class with optional boolean into map" in {
    val test = BooleanClass(Some(true))

    val result = booleanConverter.toMap(test)

    result should be(Map(
      "value" -> AttributeValue.builder().bool(true).build()
    ))
  }

  it should "converter class with empty optional boolean into map" in {
    val test = BooleanClass(None)

    val result = booleanConverter.toMap(test)

    result should be(Map(
      "value" -> AttributeValue.builder().nul(true).build()
    ))
  }

  it should "read optional boolean class from map" in {
    val map = Map("value" -> AttributeValue.builder().bool(true).build())

    val result = booleanConverter.fromMap(map)

    result should be(BooleanClass(Some(true)))
  }

  it should "read optional boolean class from map when value is empty" in {
    val map = Map("value" -> AttributeValue.builder().nul(true).build())

    val result = booleanConverter.fromMap(map)

    result should be(BooleanClass(None))
  }

  it should "read false optional boolean class from map when there is no value" in {
    val map = Map("value" -> AttributeValue.builder().build())

    val result = booleanConverter.fromMap(map)

    result should be(BooleanClass(Some(false)))
  }


}
