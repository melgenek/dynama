package dynama.mapper.ops

import dynama.mapper.{DynamoTable, SortedDynamoTable}
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration
import software.amazon.awssdk.services.dynamodb.model.{AttributeDefinition, BillingMode, CreateTableRequest, KeySchemaElement, KeyType, ProvisionedThroughput, Tag}

import scala.collection.JavaConverters._

trait CreateTableOps extends BaseOps {

  protected def createTableRequestBuilder(attributeDefinitions: Seq[AttributeDefinition],
                                          keySchemaElements: Seq[KeySchemaElement],
                                          tags: Map[String, String] = Map.empty,
                                          overrideConfiguration: Option[AwsRequestOverrideConfiguration] = None): CreateTableRequest.Builder = {
    val builder = CreateTableRequest.builder()
      .attributeDefinitions(attributeDefinitions.asJava)
      .keySchema(keySchemaElements.asJava)
      .tags(tags.map { case (key, value) => Tag.builder().key(key).value(value).build() }.toSeq.asJava)
      .tableName(tableName)

    val builderWithConfiguration = overrideConfiguration.map(builder.overrideConfiguration).getOrElse(builder)

    builderWithConfiguration
  }


}

trait SimpleCreateTableOps extends CreateTableOps {
  this: DynamoTable[_, _] =>

  def createTableRequest(tags: Map[String, String] = Map.empty,
                         overrideConfiguration: Option[AwsRequestOverrideConfiguration] = None): CreateTableRequest = {
    createTableRequestBuilder(
      Seq(AttributeDefinition.builder().attributeName(partitionKey.name).attributeType(partitionKey.typeConverter.toScalarAttributeType).build()),
      Seq(KeySchemaElement.builder().attributeName(partitionKey.name).keyType(KeyType.HASH).build()),
      tags,
      overrideConfiguration
    )
      .billingMode(BillingMode.PAY_PER_REQUEST)
      .build()
  }


  def createProvisionedTableRequest(readCapacity: Long, writeCapacity: Long,
                                    tags: Map[String, String] = Map.empty,
                                    overrideConfiguration: Option[AwsRequestOverrideConfiguration] = None): CreateTableRequest = {
    createTableRequestBuilder(
      Seq(AttributeDefinition.builder().attributeName(partitionKey.name).attributeType(partitionKey.typeConverter.toScalarAttributeType).build()),
      Seq(KeySchemaElement.builder().attributeName(partitionKey.name).keyType(KeyType.HASH).build()),
      tags,
      overrideConfiguration
    )
      .billingMode(BillingMode.PROVISIONED)
      .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(readCapacity).writeCapacityUnits(writeCapacity).build())
      .build()
  }

}

trait SortedCreateTableOps extends CreateTableOps {
  this: SortedDynamoTable[_, _, _] =>

  def createTableRequest(tags: Map[String, String] = Map.empty,
                         overrideConfiguration: Option[AwsRequestOverrideConfiguration] = None): CreateTableRequest = {
    createTableRequestBuilder(
      Seq(
        AttributeDefinition.builder().attributeName(partitionKey.name).attributeType(partitionKey.typeConverter.toScalarAttributeType).build(),
        AttributeDefinition.builder().attributeName(sortKey.name).attributeType(sortKey.typeConverter.toScalarAttributeType).build()
      ),
      Seq(
        KeySchemaElement.builder().attributeName(partitionKey.name).keyType(KeyType.HASH).build(),
        KeySchemaElement.builder().attributeName(sortKey.name).keyType(KeyType.RANGE).build()
      ),
      tags,
      overrideConfiguration
    )
      .billingMode(BillingMode.PAY_PER_REQUEST)
      .build()
  }


  def createProvisionedTableRequest(readCapacity: Long, writeCapacity: Long,
                                    tags: Map[String, String] = Map.empty,
                                    overrideConfiguration: Option[AwsRequestOverrideConfiguration] = None): CreateTableRequest = {
    createTableRequestBuilder(
      Seq(
        AttributeDefinition.builder().attributeName(partitionKey.name).attributeType(partitionKey.typeConverter.toScalarAttributeType).build(),
        AttributeDefinition.builder().attributeName(sortKey.name).attributeType(sortKey.typeConverter.toScalarAttributeType).build()
      ),
      Seq(
        KeySchemaElement.builder().attributeName(partitionKey.name).keyType(KeyType.HASH).build(),
        KeySchemaElement.builder().attributeName(sortKey.name).keyType(KeyType.RANGE).build()
      ),
      tags,
      overrideConfiguration
    )
      .billingMode(BillingMode.PROVISIONED)
      .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(readCapacity).writeCapacityUnits(writeCapacity).build())
      .build()
  }

}
