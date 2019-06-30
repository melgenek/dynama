package dynama.example

import java.net.URI

import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model._

object DynamoCreateTableTest extends App {

  val request = CreateTableRequest.builder
    .attributeDefinitions(
      AttributeDefinition.builder.attributeName("id").attributeType(ScalarAttributeType.S).build
    )
    .keySchema(
      KeySchemaElement.builder.attributeName("id").keyType(KeyType.HASH).build
    )
    .provisionedThroughput(
      ProvisionedThroughput.builder.readCapacityUnits(10L).writeCapacityUnits(10L).build
    )
    .tableName("example_table")
    .build

  val ddb = DynamoDbClient.builder()
    .endpointOverride(new URI("http://localhost:4569"))
    .build()

  try {
    val response = ddb.createTable(request)
    println(response.tableDescription.tableName)
    println(response.tableDescription.keySchema)
  } catch {
    case e: DynamoDbException =>
      println(e.getMessage)
  }

}
