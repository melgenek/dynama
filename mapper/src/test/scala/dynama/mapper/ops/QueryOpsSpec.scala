package dynama.mapper.ops

import dynama.mapper.Desc
import dynama.mapper.util.{TestTables, TestUtils}
import org.scalatest.{FlatSpec, Matchers}
import software.amazon.awssdk.services.dynamodb.model.{AttributeValue, QueryRequest, Select}

import scala.collection.JavaConverters._

class QueryOpsSpec extends FlatSpec with Matchers with TestTables {

  "DynamoTable" should "create a query request" in {
    val request = SimpleTable.queryRequest(10, Desc, limit = Some(5), consistentRead = true, Some(TestUtils.ConfigurationOverride))

    request should be(QueryRequest.builder()
      .keyConditionExpression("#pkName = :pkValue")
      .expressionAttributeNames(Map("#pkName" -> "field1").asJava)
      .expressionAttributeValues(Map(":pkValue" -> AttributeValue.builder().n("10").build()).asJava)
      .consistentRead(true)
      .scanIndexForward(false)
      .limit(5)
      .select(Select.ALL_ATTRIBUTES)
      .overrideConfiguration(TestUtils.ConfigurationOverride)
      .tableName("sample-table")
      .build()
    )
  }

  "SortedDynamoTable" should "create a query request" in {
    val request = SortedTable.queryRequest(10, Desc, limit = Some(5), consistentRead = true, Some(TestUtils.ConfigurationOverride))

    request should be(QueryRequest.builder()
      .keyConditionExpression("#pkName = :pkValue")
      .expressionAttributeNames(Map("#pkName" -> "field1").asJava)
      .expressionAttributeValues(Map(":pkValue" -> AttributeValue.builder().n("10").build()).asJava)
      .consistentRead(true)
      .scanIndexForward(false)
      .limit(5)
      .select(Select.ALL_ATTRIBUTES)
      .overrideConfiguration(TestUtils.ConfigurationOverride)
      .tableName("sample-table")
      .build()
    )
  }

  it should "create a query request with composite key" in {
    val request = SortedTable.queryRequestWithSortKey(
      10, SortedTable.sortKey > 33.33, Desc, limit = Some(5), consistentRead = true, Some(TestUtils.ConfigurationOverride)
    )

    request should be(QueryRequest.builder()
      .keyConditionExpression("#pkName = :pkValue and #customNameName > :customNameValue")
      .expressionAttributeNames(Map(
        "#pkName" -> "field1",
        "#customNameName" -> "customName"
      ).asJava)
      .expressionAttributeValues(Map(
        ":pkValue" -> AttributeValue.builder().n("10").build(),
        ":customNameValue" -> AttributeValue.builder().n("33.33").build()
      ).asJava)
      .consistentRead(true)
      .scanIndexForward(false)
      .limit(5)
      .select(Select.ALL_ATTRIBUTES)
      .overrideConfiguration(TestUtils.ConfigurationOverride)
      .tableName("sample-table")
      .build()
    )
  }

}
