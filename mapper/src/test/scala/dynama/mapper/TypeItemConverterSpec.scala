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
  val longConverter: ItemConverter[LongClass] = ItemConverter.converter[LongClass]
  val shortConverter: ItemConverter[ShortClass] = ItemConverter.converter[ShortClass]
  val byteConverter: ItemConverter[ByteClass] = ItemConverter.converter[ByteClass]
  val charConverter: ItemConverter[CharClass] = ItemConverter.converter[CharClass]
  val stringConverter: ItemConverter[StringClass] = ItemConverter.converter[StringClass]
  val booleanConverter: ItemConverter[BooleanClass] = ItemConverter.converter[BooleanClass]

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

  it should "convert short class to map" in {
    val test = ShortClass(Short.MinValue)

    val result = shortConverter.toMap(test)

    result should be(Map(
      "value" -> AttributeValue.builder().n("-32768").build()
    ))
  }

  it should "read short class from map" in {
    val map = Map("value" -> AttributeValue.builder().n("-32768").build())

    val result = shortConverter.fromMap(map)

    result should be(ShortClass(Short.MinValue))
  }

  it should "convert byte class to map" in {
    val test = ByteClass(Byte.MinValue)

    val result = byteConverter.toMap(test)

    result should be(Map(
      "value" -> AttributeValue.builder().n("-128").build()
    ))
  }

  it should "read byte class from map" in {
    val map = Map("value" -> AttributeValue.builder().n("-128").build())

    val result = byteConverter.fromMap(map)

    result should be(ByteClass(Byte.MinValue))
  }

  it should "convert char class to map" in {
    val test = CharClass('a')

    val result = charConverter.toMap(test)

    result should be(Map(
      "value" -> AttributeValue.builder().s("a").build()
    ))
  }

  it should "read char class from map" in {
    val map = Map("value" -> AttributeValue.builder().s("ab").build())

    val result = charConverter.fromMap(map)

    result should be(CharClass('a'))
  }

  it should "convert long class to map" in {
    val test = LongClass(Long.MinValue)

    val result = longConverter.toMap(test)

    result should be(Map(
      "value" -> AttributeValue.builder().n("-9223372036854775808").build()
    ))
  }

  it should "read long class from map" in {
    val map = Map("value" -> AttributeValue.builder().n("-9223372036854775808").build())

    val result = longConverter.fromMap(map)

    result should be(LongClass(Long.MinValue))
  }

  it should "convert boolean class to map" in {
    val test = BooleanClass(true)

    val result = booleanConverter.toMap(test)

    result should be(Map(
      "value" -> AttributeValue.builder().bool(true).build()
    ))
  }

  it should "read boolean class from map" in {
    val map = Map("value" -> AttributeValue.builder().bool(true).build())

    val result = booleanConverter.fromMap(map)

    result should be(BooleanClass(true))
  }

  it should "read false read boolean class from map when boolean value is not set" in {
    val map = Map("value" -> AttributeValue.builder().build())

    val result = booleanConverter.fromMap(map)

    result should be(BooleanClass(false))
  }

  it should "convert string class to map" in {
    val test = StringClass("abc")

    val result = stringConverter.toMap(test)

    result should be(Map(
      "value" -> AttributeValue.builder().s("abc").build()
    ))
  }

  it should "read string class from map" in {
    val map = Map("value" -> AttributeValue.builder().s("abc").build())

    val result = stringConverter.fromMap(map)

    result should be(StringClass("abc"))
  }

}
