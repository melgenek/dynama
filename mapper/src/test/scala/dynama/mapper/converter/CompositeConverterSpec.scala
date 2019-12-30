package dynama.mapper.converter

import dynama.converter.{Attribute, CompositeConverter}
import org.scalatest.{EitherValues, FlatSpec, Matchers}
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

import scala.collection.JavaConverters._

class CompositeConverterSpec extends FlatSpec with Matchers with EitherValues {

  case class A1(a1: String)
  implicit val A1Converter: CompositeConverter[A1] = CompositeConverter[A1](
    Attribute("a1", _.a1)
  )(A1.apply)

  case class A2(a1: String, a2: String)
  implicit val A2Converter: CompositeConverter[A2] = CompositeConverter[A2](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2)
  )(A2.apply)

  case class A3(a1: String, a2: String, a3: String)
  implicit val A3Converter: CompositeConverter[A3] = CompositeConverter[A3](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3)
  )(A3.apply)

  case class A4(a1: String, a2: String, a3: String, a4: String)
  implicit val A4Converter: CompositeConverter[A4] = CompositeConverter[A4](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4)
  )(A4.apply)

  case class A5(a1: String, a2: String, a3: String, a4: String, a5: String)
  implicit val A5Converter: CompositeConverter[A5] = CompositeConverter[A5](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5)
  )(A5.apply)

  case class A6(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String)
  implicit val A6Converter: CompositeConverter[A6] = CompositeConverter[A6](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5),
    Attribute("a6", _.a6)
  )(A6.apply)

  case class A7(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String, a7: String)
  implicit val A7Converter: CompositeConverter[A7] = CompositeConverter[A7](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5),
    Attribute("a6", _.a6),
    Attribute("a7", _.a7)
  )(A7.apply)

  case class A8(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String, a7: String, a8: String)
  implicit val A8Converter: CompositeConverter[A8] = CompositeConverter[A8](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5),
    Attribute("a6", _.a6),
    Attribute("a7", _.a7),
    Attribute("a8", _.a8)
  )(A8.apply)

  case class A9(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String, a7: String, a8: String, a9: String)
  implicit val A9Converter: CompositeConverter[A9] = CompositeConverter[A9](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5),
    Attribute("a6", _.a6),
    Attribute("a7", _.a7),
    Attribute("a8", _.a8),
    Attribute("a9", _.a9)
  )(A9.apply)

  case class A10(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String, a7: String, a8: String, a9: String, a10: String)
  implicit val A10Converter: CompositeConverter[A10] = CompositeConverter[A10](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5),
    Attribute("a6", _.a6),
    Attribute("a7", _.a7),
    Attribute("a8", _.a8),
    Attribute("a9", _.a9),
    Attribute("a10", _.a10)
  )(A10.apply)

  case class A11(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String, a7: String, a8: String, a9: String, a10: String, a11: String)
  implicit val A11Converter: CompositeConverter[A11] = CompositeConverter[A11](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5),
    Attribute("a6", _.a6),
    Attribute("a7", _.a7),
    Attribute("a8", _.a8),
    Attribute("a9", _.a9),
    Attribute("a10", _.a10),
    Attribute("a11", _.a11)
  )(A11.apply)

  case class A12(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String, a7: String, a8: String, a9: String, a10: String, a11: String, a12: String)
  implicit val A12Converter: CompositeConverter[A12] = CompositeConverter[A12](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5),
    Attribute("a6", _.a6),
    Attribute("a7", _.a7),
    Attribute("a8", _.a8),
    Attribute("a9", _.a9),
    Attribute("a10", _.a10),
    Attribute("a11", _.a11),
    Attribute("a12", _.a12)
  )(A12.apply)

  case class A13(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String, a7: String, a8: String, a9: String, a10: String, a11: String, a12: String, a13: String)
  implicit val A13Converter: CompositeConverter[A13] = CompositeConverter[A13](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5),
    Attribute("a6", _.a6),
    Attribute("a7", _.a7),
    Attribute("a8", _.a8),
    Attribute("a9", _.a9),
    Attribute("a10", _.a10),
    Attribute("a11", _.a11),
    Attribute("a12", _.a12),
    Attribute("a13", _.a13)
  )(A13.apply)

  case class A14(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String, a7: String, a8: String, a9: String, a10: String, a11: String, a12: String, a13: String, a14: String)
  implicit val A14Converter: CompositeConverter[A14] = CompositeConverter[A14](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5),
    Attribute("a6", _.a6),
    Attribute("a7", _.a7),
    Attribute("a8", _.a8),
    Attribute("a9", _.a9),
    Attribute("a10", _.a10),
    Attribute("a11", _.a11),
    Attribute("a12", _.a12),
    Attribute("a13", _.a13),
    Attribute("a14", _.a14)
  )(A14.apply)

  case class A15(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String, a7: String, a8: String, a9: String, a10: String, a11: String, a12: String, a13: String, a14: String, a15: String)
  implicit val A15Converter: CompositeConverter[A15] = CompositeConverter[A15](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5),
    Attribute("a6", _.a6),
    Attribute("a7", _.a7),
    Attribute("a8", _.a8),
    Attribute("a9", _.a9),
    Attribute("a10", _.a10),
    Attribute("a11", _.a11),
    Attribute("a12", _.a12),
    Attribute("a13", _.a13),
    Attribute("a14", _.a14),
    Attribute("a15", _.a15)
  )(A15.apply)

  case class A16(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String, a7: String, a8: String, a9: String, a10: String, a11: String, a12: String, a13: String, a14: String, a15: String, a16: String)
  implicit val A16Converter: CompositeConverter[A16] = CompositeConverter[A16](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5),
    Attribute("a6", _.a6),
    Attribute("a7", _.a7),
    Attribute("a8", _.a8),
    Attribute("a9", _.a9),
    Attribute("a10", _.a10),
    Attribute("a11", _.a11),
    Attribute("a12", _.a12),
    Attribute("a13", _.a13),
    Attribute("a14", _.a14),
    Attribute("a15", _.a15),
    Attribute("a16", _.a16)
  )(A16.apply)

  case class A17(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String, a7: String, a8: String, a9: String, a10: String, a11: String, a12: String, a13: String, a14: String, a15: String, a16: String, a17: String)
  implicit val A17Converter: CompositeConverter[A17] = CompositeConverter[A17](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5),
    Attribute("a6", _.a6),
    Attribute("a7", _.a7),
    Attribute("a8", _.a8),
    Attribute("a9", _.a9),
    Attribute("a10", _.a10),
    Attribute("a11", _.a11),
    Attribute("a12", _.a12),
    Attribute("a13", _.a13),
    Attribute("a14", _.a14),
    Attribute("a15", _.a15),
    Attribute("a16", _.a16),
    Attribute("a17", _.a17)
  )(A17.apply)

  case class A18(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String, a7: String, a8: String, a9: String, a10: String, a11: String, a12: String, a13: String, a14: String, a15: String, a16: String, a17: String, a18: String)
  implicit val A18Converter: CompositeConverter[A18] = CompositeConverter[A18](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5),
    Attribute("a6", _.a6),
    Attribute("a7", _.a7),
    Attribute("a8", _.a8),
    Attribute("a9", _.a9),
    Attribute("a10", _.a10),
    Attribute("a11", _.a11),
    Attribute("a12", _.a12),
    Attribute("a13", _.a13),
    Attribute("a14", _.a14),
    Attribute("a15", _.a15),
    Attribute("a16", _.a16),
    Attribute("a17", _.a17),
    Attribute("a18", _.a18)
  )(A18.apply)

  case class A19(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String, a7: String, a8: String, a9: String, a10: String, a11: String, a12: String, a13: String, a14: String, a15: String, a16: String, a17: String, a18: String, a19: String)
  implicit val A19Converter: CompositeConverter[A19] = CompositeConverter[A19](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5),
    Attribute("a6", _.a6),
    Attribute("a7", _.a7),
    Attribute("a8", _.a8),
    Attribute("a9", _.a9),
    Attribute("a10", _.a10),
    Attribute("a11", _.a11),
    Attribute("a12", _.a12),
    Attribute("a13", _.a13),
    Attribute("a14", _.a14),
    Attribute("a15", _.a15),
    Attribute("a16", _.a16),
    Attribute("a17", _.a17),
    Attribute("a18", _.a18),
    Attribute("a19", _.a19)
  )(A19.apply)

  case class A20(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String, a7: String, a8: String, a9: String, a10: String, a11: String, a12: String, a13: String, a14: String, a15: String, a16: String, a17: String, a18: String, a19: String, a20: String)
  implicit val A20Converter: CompositeConverter[A20] = CompositeConverter[A20](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5),
    Attribute("a6", _.a6),
    Attribute("a7", _.a7),
    Attribute("a8", _.a8),
    Attribute("a9", _.a9),
    Attribute("a10", _.a10),
    Attribute("a11", _.a11),
    Attribute("a12", _.a12),
    Attribute("a13", _.a13),
    Attribute("a14", _.a14),
    Attribute("a15", _.a15),
    Attribute("a16", _.a16),
    Attribute("a17", _.a17),
    Attribute("a18", _.a18),
    Attribute("a19", _.a19),
    Attribute("a20", _.a20)
  )(A20.apply)

  case class A21(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String, a7: String, a8: String, a9: String, a10: String, a11: String, a12: String, a13: String, a14: String, a15: String, a16: String, a17: String, a18: String, a19: String, a20: String, a21: String)
  implicit val A21Converter: CompositeConverter[A21] = CompositeConverter[A21](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5),
    Attribute("a6", _.a6),
    Attribute("a7", _.a7),
    Attribute("a8", _.a8),
    Attribute("a9", _.a9),
    Attribute("a10", _.a10),
    Attribute("a11", _.a11),
    Attribute("a12", _.a12),
    Attribute("a13", _.a13),
    Attribute("a14", _.a14),
    Attribute("a15", _.a15),
    Attribute("a16", _.a16),
    Attribute("a17", _.a17),
    Attribute("a18", _.a18),
    Attribute("a19", _.a19),
    Attribute("a20", _.a20),
    Attribute("a21", _.a21)
  )(A21.apply)

  case class A22(a1: String, a2: String, a3: String, a4: String, a5: String, a6: String, a7: String, a8: String, a9: String, a10: String, a11: String, a12: String, a13: String, a14: String, a15: String, a16: String, a17: String, a18: String, a19: String, a20: String, a21: String, a22: String)
  implicit val A22Converter: CompositeConverter[A22] = CompositeConverter[A22](
    Attribute("a1", _.a1),
    Attribute("a2", _.a2),
    Attribute("a3", _.a3),
    Attribute("a4", _.a4),
    Attribute("a5", _.a5),
    Attribute("a6", _.a6),
    Attribute("a7", _.a7),
    Attribute("a8", _.a8),
    Attribute("a9", _.a9),
    Attribute("a10", _.a10),
    Attribute("a11", _.a11),
    Attribute("a12", _.a12),
    Attribute("a13", _.a13),
    Attribute("a14", _.a14),
    Attribute("a15", _.a15),
    Attribute("a16", _.a16),
    Attribute("a17", _.a17),
    Attribute("a18", _.a18),
    Attribute("a19", _.a19),
    Attribute("a20", _.a20),
    Attribute("a21", _.a21),
    Attribute("a22", _.a22)
  )(A22.apply)

  "Class with 1 attribute" should behave like conversionCheck(A1("a1"), A1Converter)
  "Class with 2 attributes" should behave like conversionCheck(A2("a1", "a2"), A2Converter)
  "Class with 3 attributes" should behave like conversionCheck(A3("a1", "a2", "a3"), A3Converter)
  "Class with 4 attributes" should behave like conversionCheck(A4("a1", "a2", "a3", "a4"), A4Converter)
  "Class with 5 attributes" should behave like conversionCheck(A5("a1", "a2", "a3", "a4", "a5"), A5Converter)
  "Class with 6 attributes" should behave like conversionCheck(A6("a1", "a2", "a3", "a4", "a5", "a6"), A6Converter)
  "Class with 7 attributes" should behave like conversionCheck(A7("a1", "a2", "a3", "a4", "a5", "a6", "a7"), A7Converter)
  "Class with 8 attributes" should behave like conversionCheck(A8("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8"), A8Converter)
  "Class with 9 attributes" should behave like conversionCheck(A9("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"), A9Converter)
  "Class with 10 attributes" should behave like conversionCheck(A10("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10"), A10Converter)
  "Class with 11 attributes" should behave like conversionCheck(A11("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11"), A11Converter)
  "Class with 12 attributes" should behave like conversionCheck(A12("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12"), A12Converter)
  "Class with 13 attributes" should behave like conversionCheck(A13("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13"), A13Converter)
  "Class with 14 attributes" should behave like conversionCheck(A14("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14"), A14Converter)
  "Class with 15 attributes" should behave like conversionCheck(A15("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15"), A15Converter)
  "Class with 16 attributes" should behave like conversionCheck(A16("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16"), A16Converter)
  "Class with 17 attributes" should behave like conversionCheck(A17("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16", "a17"), A17Converter)
  "Class with 18 attributes" should behave like conversionCheck(A18("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16", "a17", "a18"), A18Converter)
  "Class with 19 attributes" should behave like conversionCheck(A19("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16", "a17", "a18", "a19"), A19Converter)
  "Class with 20 attributes" should behave like conversionCheck(A20("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16", "a17", "a18", "a19", "a20"), A20Converter)
  "Class with 21 attributes" should behave like conversionCheck(A21("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16", "a17", "a18", "a19", "a20", "a21"), A21Converter)
  "Class with 22 attributes" should behave like conversionCheck(A22("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16", "a17", "a18", "a19", "a20", "a21", "a22"), A22Converter)

  def conversionCheck[T](t: T, converter: CompositeConverter[T]): Unit = {
    it should "be processed" in {
      val result = converter.decode(converter.encode(t))

      result.right.value should be(t)
    }
  }
}
