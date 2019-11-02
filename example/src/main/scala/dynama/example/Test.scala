package dynama.example

import java.net.URI
import java.time.Instant

import dynama.mapper._
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient


object Test extends App {
  case class C(length: Int, date: Instant)

  implicit val InstantConverter: AttributeConverter[Instant] = AttributeConverter.StringConverter.imap(_.toString, Instant.parse)


  val client = DynamoDbAsyncClient.builder()
    .endpointOverride(new URI("http://localhost:4569"))
    .build()


}

