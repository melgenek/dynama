package dynama.mapper.converter

import dynama.converter.{Attribute, CompositeConverter, DecodingError}
import org.scalatest.{EitherValues, FlatSpec, Matchers}
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

class TypeCompositeConverterSpec extends FlatSpec with Matchers with EitherValues {

  case class IntClass(value: Int)
  case class DoubleClass(value: Double)
  case class BooleanClass(value: Boolean)
  case class ByteClass(value: Byte)
  case class ShortClass(value: Short)
  case class LongClass(value: Long)
  case class FloatClass(value: Float)
  case class CharClass(value: Char)
  case class StringClass(value: String)
  case class BigDecimalClass(value: BigDecimal)

  val intConverter: CompositeConverter[IntClass] = CompositeConverter[IntClass](
    Attribute("value", _.value)
  )(IntClass.apply)
  val doubleConverter: CompositeConverter[DoubleClass] = CompositeConverter[DoubleClass](
    Attribute("value", _.value)
  )(DoubleClass.apply)
  val floatConverter: CompositeConverter[FloatClass] = CompositeConverter[FloatClass](
    Attribute("value", _.value)
  )(FloatClass.apply)
  val longConverter: CompositeConverter[LongClass] = CompositeConverter[LongClass](
    Attribute("value", _.value)
  )(LongClass.apply)
  val shortConverter: CompositeConverter[ShortClass] = CompositeConverter[ShortClass](
    Attribute("value", _.value)
  )(ShortClass.apply)
  val byteConverter: CompositeConverter[ByteClass] = CompositeConverter[ByteClass](
    Attribute("value", _.value)
  )(ByteClass.apply)
  val charConverter: CompositeConverter[CharClass] = CompositeConverter[CharClass](
    Attribute("value", _.value)
  )(CharClass.apply)
  val stringConverter: CompositeConverter[StringClass] = CompositeConverter[StringClass](
    Attribute("value", _.value)
  )(StringClass.apply)
  val booleanConverter: CompositeConverter[BooleanClass] = CompositeConverter[BooleanClass](
    Attribute("value", _.value)
  )(BooleanClass.apply)
  val bigDecimalConverter: CompositeConverter[BigDecimalClass] = CompositeConverter[BigDecimalClass](
    Attribute("value", _.value)
  )(BigDecimalClass.apply)

  "CompositeConverter" should "encode int class to map" in {
    val test = IntClass(Int.MinValue)

    val result = intConverter.encode(test)

    result should be(Map(
      "value" -> AttributeValue.builder().n("-2147483648").build()
    ))
  }

  it should "decode int class" in {
    val map = Map("value" -> AttributeValue.builder().n("-2147483648").build())

    val result = intConverter.decode(map)

    result.right.value should be(IntClass(Int.MinValue))
  }

  it should "not decode invalid int" in {
    val map = Map("value" -> AttributeValue.builder().n("-2147483649").build())

    val result = intConverter.decode(map)

    result.left.value shouldBe a[DecodingError]
  }

  it should "not decode double into int class" in {
    val map = Map("value" -> AttributeValue.builder().n("20.12").build())

    val result = intConverter.decode(map)

    result.left.value shouldBe a[DecodingError]
  }

  it should "not decode string into int class" in {
    val map = Map("value" -> AttributeValue.builder().s("a string").build())

    val result = intConverter.decode(map)

    result.left.value shouldBe a[DecodingError]
  }

  it should "encode double class to map" in {
    val test = DoubleClass(Double.MinValue)

    val result = doubleConverter.encode(test)

    result should be(Map(
      "value" -> AttributeValue.builder().n("-1.7976931348623157E308").build()
    ))
  }

  it should "decode double class" in {
    val map = Map("value" -> AttributeValue.builder().n("-1.7976931348623157E308").build())

    val result = doubleConverter.decode(map)

    result.right.value should be(DoubleClass(Double.MinValue))
  }

  it should "encode float class to map" in {
    val test = FloatClass(Float.MinValue)

    val result = floatConverter.encode(test)

    result should be(Map(
      "value" -> AttributeValue.builder().n("-3.4028235E38").build()
    ))
  }

  it should "decode float class" in {
    val map = Map("value" -> AttributeValue.builder().n("-3.4028235E38").build())

    val result = floatConverter.decode(map)

    result.right.value should be(FloatClass(Float.MinValue))
  }

  it should "encode short class to map" in {
    val test = ShortClass(Short.MinValue)

    val result = shortConverter.encode(test)

    result should be(Map(
      "value" -> AttributeValue.builder().n("-32768").build()
    ))
  }

  it should "decode short class" in {
    val map = Map("value" -> AttributeValue.builder().n("-32768").build())

    val result = shortConverter.decode(map)

    result.right.value should be(ShortClass(Short.MinValue))
  }

  it should "encode byte class to map" in {
    val test = ByteClass(Byte.MinValue)

    val result = byteConverter.encode(test)

    result should be(Map(
      "value" -> AttributeValue.builder().n("-128").build()
    ))
  }

  it should "decode byte class" in {
    val map = Map("value" -> AttributeValue.builder().n("-128").build())

    val result = byteConverter.decode(map)

    result.right.value should be(ByteClass(Byte.MinValue))
  }

  it should "encode char class to map" in {
    val test = CharClass('a')

    val result = charConverter.encode(test)

    result should be(Map(
      "value" -> AttributeValue.builder().s("a").build()
    ))
  }

  it should "decode char class" in {
    val map = Map("value" -> AttributeValue.builder().s("ab").build())

    val result = charConverter.decode(map)

    result.right.value should be(CharClass('a'))
  }

  it should "not decode char class when the string is empty" in {
    val map = Map("value" -> AttributeValue.builder().s("").build())

    val result = charConverter.decode(map)

    result.left.value shouldBe a[DecodingError]
  }

  it should "encode long class to map" in {
    val test = LongClass(Long.MinValue)

    val result = longConverter.encode(test)

    result should be(Map(
      "value" -> AttributeValue.builder().n("-9223372036854775808").build()
    ))
  }

  it should "decode long class" in {
    val map = Map("value" -> AttributeValue.builder().n("-9223372036854775808").build())

    val result = longConverter.decode(map)

    result.right.value should be(LongClass(Long.MinValue))
  }

  it should "encode boolean class to map" in {
    val test = BooleanClass(true)

    val result = booleanConverter.encode(test)

    result should be(Map(
      "value" -> AttributeValue.builder().bool(true).build()
    ))
  }

  it should "decode boolean class" in {
    val map = Map("value" -> AttributeValue.builder().bool(true).build())

    val result = booleanConverter.decode(map)

    result.right.value should be(BooleanClass(true))
  }

  it should "decode false boolean class when boolean value is not set" in {
    val map = Map("value" -> AttributeValue.builder().build())

    val result = booleanConverter.decode(map)

    result.right.value should be(BooleanClass(false))
  }

  it should "encode string class to map" in {
    val test = StringClass("abc")

    val result = stringConverter.encode(test)

    result should be(Map(
      "value" -> AttributeValue.builder().s("abc").build()
    ))
  }

  it should "decode string class" in {
    val map = Map("value" -> AttributeValue.builder().s("abc").build())

    val result = stringConverter.decode(map)

    result.right.value should be(StringClass("abc"))
  }

  it should "encode big decimal class to map" in {
    val test = BigDecimalClass(BigDecimal("1234123413413233.22134"))

    val result = bigDecimalConverter.encode(test)

    result should be(Map(
      "value" -> AttributeValue.builder().n("1234123413413233.22134").build()
    ))
  }

  it should "decode big decimal class" in {
    val map = Map("value" -> AttributeValue.builder().n("1234123413413233.22134").build())

    val result = bigDecimalConverter.decode(map)

    result.right.value should be(BigDecimalClass(BigDecimal("1234123413413233.22134")))
  }

}
