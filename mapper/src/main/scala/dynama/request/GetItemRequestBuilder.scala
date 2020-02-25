package dynama.request

import dynama.table.DynamoTable
import software.amazon.awssdk.services.dynamodb.model.{AttributeValue, GetItemRequest}

import scala.collection.JavaConverters._

trait GetItemRequestBuilder extends BaseBuilders {

  protected def get(keys: Map[String, AttributeValue]): GetItemRequest.Builder = {
    GetItemRequest.builder()
      .key(keys.asJava)
      .tableName(tableName)
  }

}

trait SimpleGetItemRequestBuilder[T, K] extends GetItemRequestBuilder {
  _: DynamoTable[T, K] =>

  def get(partitionKeyValue: K): DynamoRequestWithResponse[GetItemRequest.Builder, T] = {
    DynamoRequestWithResponse(
      get(partitionKey.converter(partitionKey.name).encode(partitionKeyValue))
    )
  }

}
