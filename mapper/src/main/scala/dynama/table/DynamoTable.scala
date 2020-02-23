package dynama.table

import dynama.request.{PutItemRequestBuilder, SimpleGetItemRequestBuilder}
import dynama.converter.CompositeConverter

abstract class DynamoTable[T, K](val tableName: String)
  extends SimpleGetItemRequestBuilder[T, K]
    with PutItemRequestBuilder[T] {

  val partitionKey: PartitionKey[K]

  implicit val converter: CompositeConverter[T]

}
