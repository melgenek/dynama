package dynama.example

import dynama.mapper.{DynamoTable, ItemConverter, SortedDynamoTable}

import scala.language.{higherKinds, implicitConversions}


object ATest extends App {

  case class C(field: Int, field2: Double)

  implicit val cConverter: ItemConverter[C] = ItemConverter.converter[C]

  object SimpleTable extends DynamoTable[C, Int]("sample-table") {
    val partitionKey = partitionAttribute(_.field)
  }

  SimpleTable.getItemRequest(10)


  object MyTable extends SortedDynamoTable[C, Int, Int]("sample-table") {
    val partitionKey = partitionAttribute(_.field)
    val sortKey = sortAttribute(_.field)

  }

 println(MyTable.queryRequest(10, MyTable.sortKey === 20))


}

