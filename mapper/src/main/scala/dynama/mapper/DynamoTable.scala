package dynama.mapper

import dynama.mapper.ops.{CompositeKeyOps, PutItemOps, SimpleGetItemOps, SimpleQueryOps, SingleKeyOps, SortedGetItemOps, SortedQueryOps}

abstract class DynamoTable[T: ItemWriter, K: AttributeConverter](val tableName: String)
  extends SingleKeyOps[T, K]
    with SimpleGetItemOps[K]
    with SimpleQueryOps[K]
    with PutItemOps[T] {
  val writer = implicitly[ItemWriter[T]]
}

abstract class SortedDynamoTable[T: ItemWriter, K: AttributeConverter, S: AttributeConverter](val tableName: String)
  extends CompositeKeyOps[T, K, S]
    with SortedGetItemOps[K, S]
    with SortedQueryOps[K, S]
    with PutItemOps[T] {
  val writer = implicitly[ItemWriter[T]]
}
