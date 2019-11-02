package dynama.mapper.ops

import dynama.mapper._
import org.scalatest.{FlatSpec, Matchers}

class KeyOpsSpec extends FlatSpec with Matchers {

  case class Example(field1: Int, field2: Double, @DynamoAttribute("customName") field3: String)

  object SimpleTable extends DynamoTable[Example, Int]("sample-table") {
    val partitionKey = partitionAttribute(_.field1)
  }

  object SimpleTableWithRenamedKey extends DynamoTable[Example, String]("sample-table") {
    val partitionKey = partitionAttribute(_.field3)
  }

  object SortedTable extends SortedDynamoTable[Example, Int, Double]("sample-table") {
    val partitionKey = partitionAttribute(_.field1)
    val sortKey = sortAttribute(_.field2)
  }

  "DynamoTable" should "have a partition key" in {
    SimpleTable.partitionKey should be(PartitionKey[Int]("field1"))
    SimpleTable.partitionKey.converter should be(AttributeConverter.IntConverter)
  }

  it should "have a partition key when the field is renamed" in {
    SimpleTableWithRenamedKey.partitionKey should be(PartitionKey[Int]("customName"))
    SimpleTableWithRenamedKey.partitionKey.converter should be(AttributeConverter.StringConverter)
  }

  "SortedDynamoTable" should "have a composite partition key" in {
    SortedTable.partitionKey should be(PartitionKey[Int]("field1"))
    SortedTable.partitionKey.converter should be(AttributeConverter.IntConverter)

    SortedTable.sortKey should be(SortKey[Double]("field2"))
    SortedTable.sortKey.converter should be(AttributeConverter.DoubleConverter)
  }

}
