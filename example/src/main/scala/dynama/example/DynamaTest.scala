package dynama.example

import java.net.URI

import dynama.mapper.client.{CustomOperations, DynamoClient}
import dynama.mapper.{DynamoTable, ItemConverter}
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object DynamaTest extends App {

  case class Example(field1: String)
  case class Example2(field1: String, field2: Option[Int])

  implicit val exampleConverter = ItemConverter.converter[Example]
  implicit val example2Converter = ItemConverter.converter[Example2]

  object ExampleTable extends DynamoTable[Example, String]("example-table") {
    override val partitionKey = partitionAttribute(_.field1)
  }

  val ddb = DynamoDbAsyncClient.builder()
    .endpointOverride(new URI("http://localhost:4569"))
    .build()

  val dynamoClient = new DynamoClient[Future, Example](ddb) with CustomOperations[Future]
  val dynamoClient2 = new DynamoClient[Future, Example2](ddb) with CustomOperations[Future]

  val resultFuture = for {
    _ <- dynamoClient.createTableAndWaitForActiveStatus(ExampleTable.createTableRequest(), ExampleTable.describeTableRequest()).recover { case _ => () }
    _ <- dynamoClient.putItem(ExampleTable.putItemRequest(Example("123")))
    storedExample <- dynamoClient2.getItem(ExampleTable.getItemRequest("123", consistentRead = true))
    _ <- dynamoClient.deleteTableAndWaitForDeletedStatus(ExampleTable.deleteTableRequest(), ExampleTable.describeTableRequest())
  } yield storedExample

  val storedItem = Await.result(resultFuture, Duration.Inf)

  println(storedItem)

  ddb.close()
  dynamoClient.close()
}
