package dynama.mapper

import org.scalatest.{FlatSpec, Matchers}

class DynamoTableConverterSpec extends FlatSpec with Matchers {

  case class SimpleClass(field1: Int, field2: String)

  "DynamoTable" should "be create" in {
    implicit val itemConverter: ItemConverter[SimpleClass] = ItemConverter.converter[SimpleClass]
    implicit val keyDefinition: KeyDefinition[SimpleClass] = KeyDefinition.partitionKey(_.field2)

    val table = DynamoTable[SimpleClass]("simple_table")

    table should be(
      DynamoTable(
        name = "simple_table"
      )(
        itemConverter = itemConverter,
        keyDefinition = keyDefinition
      )
    )
  }

}
