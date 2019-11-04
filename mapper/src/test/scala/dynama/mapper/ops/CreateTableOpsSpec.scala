package dynama.mapper.ops

import dynama.mapper.util.{TestTables, TestUtils}
import org.scalatest.{FlatSpec, Matchers}
import software.amazon.awssdk.services.dynamodb.model.{AttributeDefinition, BillingMode, CreateTableRequest, KeySchemaElement, KeyType, ProvisionedThroughput, ScalarAttributeType, Tag}

class CreateTableOpsSpec extends FlatSpec with Matchers with TestTables {

  "DynamoTable" should "create a create table request" in {
    val request = SimpleTable.createTableRequest(Map("key" -> "value"), Some(TestUtils.ConfigurationOverride))

    request should be(CreateTableRequest.builder()
      .attributeDefinitions(AttributeDefinition.builder().attributeName("field1").attributeType(ScalarAttributeType.N).build())
      .keySchema(KeySchemaElement.builder().attributeName("field1").keyType(KeyType.HASH).build())
      .billingMode(BillingMode.PAY_PER_REQUEST)
      .tags(Tag.builder().key("key").value("value").build())
      .overrideConfiguration(TestUtils.ConfigurationOverride)
      .tableName("sample-table")
      .build()
    )
  }

  it should "create a provisioned create table request" in {
    val request = SimpleTable.createProvisionedTableRequest(readCapacity = 10, writeCapacity = 20, Map("key" -> "value"), Some(TestUtils.ConfigurationOverride))

    request should be(CreateTableRequest.builder()
      .attributeDefinitions(AttributeDefinition.builder().attributeName("field1").attributeType(ScalarAttributeType.N).build())
      .keySchema(KeySchemaElement.builder().attributeName("field1").keyType(KeyType.HASH).build())
      .billingMode(BillingMode.PROVISIONED)
      .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(10L).writeCapacityUnits(20L).build())
      .tags(Tag.builder().key("key").value("value").build())
      .overrideConfiguration(TestUtils.ConfigurationOverride)
      .tableName("sample-table")
      .build()
    )
  }

  "SortedDynamoTable" should "create a create table request" in {
    val request = SortedTable.createTableRequest(Map("key" -> "value"), Some(TestUtils.ConfigurationOverride))

    request should be(CreateTableRequest.builder()
      .attributeDefinitions(
        AttributeDefinition.builder().attributeName("field1").attributeType(ScalarAttributeType.N).build(),
        AttributeDefinition.builder().attributeName("customName").attributeType(ScalarAttributeType.N).build()
      )
      .keySchema(
        KeySchemaElement.builder().attributeName("field1").keyType(KeyType.HASH).build(),
        KeySchemaElement.builder().attributeName("customName").keyType(KeyType.RANGE).build()
      )
      .billingMode(BillingMode.PAY_PER_REQUEST)
      .tags(Tag.builder().key("key").value("value").build())
      .overrideConfiguration(TestUtils.ConfigurationOverride)
      .tableName("sample-table")
      .build()
    )
  }

  it should "create a provisioned create table request" in {
    val request = SortedTable.createProvisionedTableRequest(readCapacity = 10, writeCapacity = 20, Map("key" -> "value"), Some(TestUtils.ConfigurationOverride))

    request should be(CreateTableRequest.builder()
      .attributeDefinitions(
        AttributeDefinition.builder().attributeName("field1").attributeType(ScalarAttributeType.N).build(),
        AttributeDefinition.builder().attributeName("customName").attributeType(ScalarAttributeType.N).build()
      )
      .keySchema(
        KeySchemaElement.builder().attributeName("field1").keyType(KeyType.HASH).build(),
        KeySchemaElement.builder().attributeName("customName").keyType(KeyType.RANGE).build()
      )
      .billingMode(BillingMode.PROVISIONED)
      .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(10L).writeCapacityUnits(20L).build())
      .tags(Tag.builder().key("key").value("value").build())
      .overrideConfiguration(TestUtils.ConfigurationOverride)
      .tableName("sample-table")
      .build()
    )
  }

}
