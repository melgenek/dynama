package dynama.example

import java.time.Instant

import dynama.mapper.{DynamoAttribute, AttributeConverter, DynamoRecord, ItemConverter}
import software.amazon.awssdk.services.dynamodb.model.AttributeValue


object Test extends App {

  case class A(value: Int, @DynamoAttribute("anderer") another: Double)

  case class B(_name: String, `more-nested`: C)

  case class C(length: Int, date: Instant)

  implicit val InstantConverter: AttributeConverter[Instant] = AttributeConverter.StringConverter.imap(_.toString, Instant.parse)

  implicit val CustomStringConverter: AttributeConverter[Int] =
    AttributeConverter.StringConverter.imap("Prefixed::" + _, _.replace("Prefixed::", "").toInt)

  implicit val CConverter: AttributeConverter[C] = AttributeConverter.StringConverter.imap(
    { c => s"${c.date}::${c.length}" },
    { value =>
      val parts = value.split("::")
      C(parts(1).toInt, Instant.parse(parts(0)))
    }
  )

  val converter: ItemConverter[A] = DynamoRecord.converter[A]

  //  private val a = A(123, 3.0, B("cool!", C(11, Instant.now())))
  private val a = A(123, 3.0)
  val generatedMap: Map[String, AttributeValue] = converter.toMap(a)
  generatedMap.foreach {
    println
  }

  println(converter.fromMap(generatedMap))


}
