package dynama.mapper.ops

import dynama.mapper._
import dynama.mapper.util.TestTables
import org.scalatest.{FlatSpec, Matchers}

class KeyOpsSpec extends FlatSpec with Matchers with TestTables {

  "DynamoTable" should "have a partition key" in {
    SimpleTable.partitionKey should be(PartitionKey[Int]("field1"))
    SimpleTable.partitionKey.converter should be(AttributeConverter.IntConverter)
  }

  "SortedDynamoTable" should "have a composite partition key" in {
    SortedTable.partitionKey should be(PartitionKey[Int]("field1"))
    SortedTable.partitionKey.converter should be(AttributeConverter.IntConverter)

    SortedTable.sortKey should be(SortKey[Double]("customName"))
    SortedTable.sortKey.converter should be(AttributeConverter.DoubleConverter)
  }

}
