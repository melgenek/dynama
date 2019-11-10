package dynama.request

import dynama.table.DynamoTable
import software.amazon.awssdk.services.dynamodb.model.{PutItemRequest, ReturnValue}

import scala.collection.JavaConverters._

trait PutItemRequestBuilder[T] {
  _: DynamoTable[T, _] =>

  def put(item: T): DynamoRequest[PutItemRequest.Builder] = {
    DynamoRequest(
      converter.encode(item)
        .map { encodedItem =>
          PutItemRequest.builder()
            .item(encodedItem.asJava)
            .tableName(tableName)
        }
    )
  }

  def putAndGetOld(item: T): DynamoRequestWithResponse[PutItemRequest.Builder, T] = {
    put(item)
      .map(_.returnValues(ReturnValue.ALL_OLD))
      .withResponse[T]
  }

}
