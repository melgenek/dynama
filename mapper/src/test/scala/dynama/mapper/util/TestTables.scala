package dynama.mapper.util

import dynama.mapper.{DynamoAttribute, DynamoTable, ItemConverter, SortedDynamoTable}

trait TestTables {

  case class Example(field1: Int, @DynamoAttribute("customName") field2: Double, field3: String)

  implicit val converter: ItemConverter[Example] = ItemConverter.converter[Example]

  object SimpleTable extends DynamoTable[Example, Int]("sample-table") {
    val partitionKey = partitionAttribute(_.field1)
  }

  object SortedTable extends SortedDynamoTable[Example, Int, Double]("sample-table") {
    val partitionKey = partitionAttribute(_.field1)
    val sortKey = sortAttribute(_.field2)
  }

}
