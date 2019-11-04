package dynama.mapper.ops

import dynama.mapper.util.{TestTables, TestUtils}
import org.scalatest.{FlatSpec, Matchers}
import software.amazon.awssdk.services.dynamodb.model.{AttributeValue, PutItemRequest}

import scala.collection.JavaConverters._

class PutItemOpsSpec extends FlatSpec with Matchers with TestTables {

  "DynamoTable" should "create a put item request" in {
    val example = Example(10, 33.33, "cool")

    val request = SimpleTable.putItemRequest(example, Some(TestUtils.ConfigurationOverride))

    request should be(PutItemRequest.builder()
      .item(Map(
        "field1" -> AttributeValue.builder().n("10").build(),
        "customName" -> AttributeValue.builder().n("33.33").build(),
        "field3" -> AttributeValue.builder().s("cool").build()
      ).asJava)
      .overrideConfiguration(TestUtils.ConfigurationOverride)
      .tableName("sample-table")
      .build()
    )
  }

  "SortedDynamoTable" should "create a put item request" in {
    val example = Example(10, 33.33, "cool")

    val request = SortedTable.putItemRequest(example, Some(TestUtils.ConfigurationOverride))

    request should be(PutItemRequest.builder()
      .item(Map(
        "field1" -> AttributeValue.builder().n("10").build(),
        "customName" -> AttributeValue.builder().n("33.33").build(),
        "field3" -> AttributeValue.builder().s("cool").build()
      ).asJava)
      .overrideConfiguration(TestUtils.ConfigurationOverride)
      .tableName("sample-table")
      .build()
    )
  }

}
