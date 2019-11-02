package dynama.mapper

import dynama.mapper.ops.{CompositeKeyOps, SimpleGetItemOps, SimpleQueryOps, SingleKeyOps, SortedGetItemOps, SortedQueryOps}

abstract class DynamoTable[T, K: AttributeConverter](val tableName: String)
  extends SingleKeyOps[T, K]
    with SimpleGetItemOps[K]
    with SimpleQueryOps[K]

abstract class SortedDynamoTable[T, K: AttributeConverter, S: AttributeConverter](val tableName: String)
  extends CompositeKeyOps[T, K, S]
    with SortedGetItemOps[K, S]
    with SortedQueryOps[K, S]
