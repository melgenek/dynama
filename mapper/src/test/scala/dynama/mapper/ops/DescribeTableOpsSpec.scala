package dynama.mapper.ops

import dynama.mapper.util.{TestTables, TestUtils}
import org.scalatest.{FlatSpec, Matchers}
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest

class DescribeTableOpsSpec extends FlatSpec with Matchers with TestTables {

  "DynamoTable" should "create a describe table request" in {
    val request = SimpleTable.describeTableRequest()

    request should be(DescribeTableRequest.builder()
      .tableName("sample-table")
      .build()
    )
  }

  it should "create a describe table request with configuration" in {
    val request = SimpleTable.describeTableRequestWithConfiguration(TestUtils.ConfigurationOverride)

    request should be(DescribeTableRequest.builder()
      .overrideConfiguration(TestUtils.ConfigurationOverride)
      .tableName("sample-table")
      .build()
    )
  }

  "SortedDynamoTable" should "create a delete table request" in {
    val request = SortedTable.describeTableRequest()

    request should be(DescribeTableRequest.builder()
      .tableName("sample-table")
      .build()
    )
  }

  it should "create a delete table request with configuration" in {
    val request = SortedTable.describeTableRequestWithConfiguration(TestUtils.ConfigurationOverride)

    request should be(DescribeTableRequest.builder()
      .overrideConfiguration(TestUtils.ConfigurationOverride)
      .tableName("sample-table")
      .build()
    )
  }

}
