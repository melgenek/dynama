package dynama.mapper

import dynama.mapper.ItemConverter.InvalidAttributeException
import org.scalatest.{FlatSpec, Matchers}
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

class TypeItemConverterSpec extends FlatSpec with Matchers {

  case class IntClass(value: Int)
  case class DoubleClass(value: Double)
  case class BooleanClass(value: Boolean)
  case class ByteClass(value: Byte)
  case class ShortClass(value: Short)
  case class LongClass(value: Long)
  case class FloatClass(value: Float)
  case class CharClass(value: Char)
  case class StringClass(value: String)

  val intConverter: ItemConverter[IntClass] = ItemConverter.converter[IntClass]
  val doubleConverter: ItemConverter[DoubleClass] = ItemConverter.converter[DoubleClass]
  val floatConverter: ItemConverter[FloatClass] = ItemConverter.converter[FloatClass]

  "ItemConverter" should "convert int class to map" in {
    val test = IntClass(Int.MinValue)

    val result = intConverter.toMap(test)

    result should be(Map(
      "value" -> AttributeValue.builder().n("-2147483648").build()
    ))
  }

  it should "read int class from map" in {
    val map = Map("value" -> AttributeValue.builder().n("-2147483648").build())

    val result = intConverter.fromMap(map)

    result should be(IntClass(Int.MinValue))
  }


  it should "throw exception when read invalid int from map" in {
    val map = Map("value" -> AttributeValue.builder().n("-2147483649").build())

    a[InvalidAttributeException] should be thrownBy {
      intConverter.fromMap(map)
    }
  }

  it should "throw exception when read double from map into int class" in {
    val map = Map("value" -> AttributeValue.builder().n("20.12").build())

    a[InvalidAttributeException] should be thrownBy {
      intConverter.fromMap(map)
    }
  }

  it should "throw exception when read string from map into int class" in {
    val map = Map("value" -> AttributeValue.builder().s("a string").build())

    a[InvalidAttributeException] should be thrownBy {
      intConverter.fromMap(map)
    }
  }

  it should "convert double class to map" in {
    val test = DoubleClass(Double.MinValue)

    val result = doubleConverter.toMap(test)

    result should be(Map(
      "value" -> AttributeValue.builder().n("-1.7976931348623157E308").build()
    ))
  }

  it should "read double class from map" in {
    val map = Map("value" -> AttributeValue.builder().n("-1.7976931348623157E308").build())

    val result = doubleConverter.fromMap(map)

    result should be(DoubleClass(Double.MinValue))
  }

  it should "convert float class to map" in {
    val test = FloatClass(Float.MinValue)

    val result = floatConverter.toMap(test)

    result should be(Map(
      "value" -> AttributeValue.builder().n("-3.4028235E38").build()
    ))
  }

  it should "read float class from map" in {
    val map = Map("value" -> AttributeValue.builder().n("-3.4028235E38").build())

    val result = floatConverter.fromMap(map)

    result should be(FloatClass(Float.MinValue))
  }


}
