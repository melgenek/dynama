package dynama.example

import java.net.URI
import java.util.UUID

import dynama.mapper.{AttributeConverter, ItemConverter}
import software.amazon.awssdk.services.dynamodb.model.{AttributeValue, GetItemRequest}

import scala.collection.JavaConverters._

object DynamoTest extends App {

  case class ProductModel(id: UUID, productName: String, price: Double)

  implicit val uuidConverter: AttributeConverter[UUID] = AttributeConverter.StringConverter.imap(_.toString, UUID.fromString)
  val converter = ItemConverter.converter[ProductModel]

  val productId = UUID.randomUUID()
  val product = ProductModel(productId, "product#1", 23.39)

  import software.amazon.awssdk.services.dynamodb.DynamoDbClient
  import software.amazon.awssdk.services.dynamodb.model.PutItemRequest

  val ddb = DynamoDbClient.builder()
    .endpointOverride(new URI("http://localhost:4569"))
    .build()

  private val javaMap = converter.toMap(product).asJava
  println(javaMap)
  val putRequest = PutItemRequest.builder
    .tableName("example_table")
    .item(javaMap)
    .build

  val getRequest = GetItemRequest.builder()
    .key(Map("id" -> AttributeValue.builder().s(productId.toString).build()).asJava)
    .tableName("example_table")
    .build()

  import software.amazon.awssdk.services.dynamodb.model.{DynamoDbException, ResourceNotFoundException}

  try {
    ddb.putItem(putRequest)
    val result = ddb.getItem(getRequest)
    println(converter.fromMap(result.item().asScala.toMap))
  } catch {
    case _: ResourceNotFoundException =>
      println(s"Error: The table can't be found")
    case e: DynamoDbException =>
      println(e.getMessage)
  }

}
