package dynama.mapper.ops

import dynama.mapper.util.{TestTables, TestUtils}
import org.scalatest.{FlatSpec, Matchers}
import software.amazon.awssdk.services.dynamodb.model.{AttributeValue, GetItemRequest}

import scala.collection.JavaConverters._

class GetItemOpsSpec extends FlatSpec with Matchers with TestTables {

  "DynamoTable" should "create a get item request" in {
    val request = SimpleTable.getItemRequest(10, consistentRead = true, Some(TestUtils.ConfigurationOverride))

    request should be(GetItemRequest.builder()
      .key(Map("field1" -> AttributeValue.builder().n("10").build()).asJava)
      .consistentRead(true)
      .overrideConfiguration(TestUtils.ConfigurationOverride)
      .tableName("sample-table")
      .build()
    )
  }

  "SortedDynamoTable" should "create a get item request" in {
    val request = SortedTable.getItemRequest(10, 33.33, consistentRead = true, Some(TestUtils.ConfigurationOverride))

    request should be(GetItemRequest.builder()
      .key(Map(
        "field1" -> AttributeValue.builder().n("10").build(),
        "customName" -> AttributeValue.builder().n("33.33").build()
      ).asJava)
      .consistentRead(true)
      .overrideConfiguration(TestUtils.ConfigurationOverride)
      .tableName("sample-table")
      .build()
    )
  }

}
