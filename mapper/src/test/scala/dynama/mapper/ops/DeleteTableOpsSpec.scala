package dynama.mapper.ops

import dynama.mapper.util.{TestTables, TestUtils}
import org.scalatest.{FlatSpec, Matchers}
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest

class DeleteTableOpsSpec extends FlatSpec with Matchers with TestTables {

  "DynamoTable" should "create a delete table request" in {
    val request = SimpleTable.deleteTableRequest()

    request should be(DeleteTableRequest.builder()
      .tableName("sample-table")
      .build()
    )
  }

  it should "create a delete table request with configuration" in {
    val request = SimpleTable.deleteTableRequestWithConfiguration(TestUtils.ConfigurationOverride)

    request should be(DeleteTableRequest.builder()
      .overrideConfiguration(TestUtils.ConfigurationOverride)
      .tableName("sample-table")
      .build()
    )
  }

  "SortedDynamoTable" should "create a delete table request" in {
    val request = SortedTable.deleteTableRequest()

    request should be(DeleteTableRequest.builder()
      .tableName("sample-table")
      .build()
    )
  }

  it should "create a delete table request with configuration" in {
    val request = SortedTable.deleteTableRequestWithConfiguration(TestUtils.ConfigurationOverride)

    request should be(DeleteTableRequest.builder()
      .overrideConfiguration(TestUtils.ConfigurationOverride)
      .tableName("sample-table")
      .build()
    )
  }

}
