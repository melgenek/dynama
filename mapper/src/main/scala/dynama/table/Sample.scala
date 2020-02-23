package dynama.table

import java.net.URI

import dynama.client.DynamoClient
import dynama.converter.CompositeConverter
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient

import scala.concurrent.Future

object Sample extends App {

  case class Example(field1: String)

  object ExampleTable extends DynamoTable[Example, String]("example_table") {
    val partitionKey = PartitionKey[String]("field1")

    val converter = CompositeConverter[Example](
      partitionKey.toAttribute(_.field1)
    )(Example.apply)
  }

  val ddb = DynamoDbAsyncClient.builder()
    .endpointOverride(new URI("http://localhost:4569"))
    .build()

  val client = new DynamoClient[Future](ddb)

  client.run(ExampleTable.put(Example("123")))
  client.run(ExampleTable.putAndGetOld(Example("123")))
  client.run(ExampleTable.get("123"))
}
