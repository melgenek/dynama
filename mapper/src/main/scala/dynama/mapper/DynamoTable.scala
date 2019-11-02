package dynama.mapper

import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration
import software.amazon.awssdk.services.dynamodb.model._

import scala.collection.JavaConverters._

abstract class DynamoTable[T: ItemConverter, K: AttributeConverter](tableName: String) {

  val partitionKey: PartitionKey[K]

  // todo
  protected def partitionAttribute(f: T => K): PartitionKey[K] = PartitionKey[K]("fake_name")

  def getItemRequest(partitionKeyValue: K,
                     consistentRead: Boolean = false,
                     returnConsumedCapacity: Option[ReturnConsumedCapacity] = None,
                     overrideConfiguration: Option[AwsRequestOverrideConfiguration] = None): GetItemRequest = {
    val builder = GetItemRequest.builder()
      .key(Map(
        partitionKey.name -> partitionKey.converter.toAttribute(partitionKeyValue)
      ).asJava)
      .consistentRead(consistentRead)
      .tableName(tableName)

    val builderWithCapacity = returnConsumedCapacity.map(builder.returnConsumedCapacity).getOrElse(builder)
    val builderWithConfiguration = overrideConfiguration.map(builderWithCapacity.overrideConfiguration).getOrElse(builderWithCapacity)

    builderWithConfiguration.build()
  }

  def queryRequest(partitionKeyValue: K,
                   queryOrder: QueryOrder = Asc,
                   limit: Option[Int] = None,
                   returnConsumedCapacity: Option[ReturnConsumedCapacity] = None,
                   overrideConfiguration: Option[AwsRequestOverrideConfiguration] = None): QueryRequest = {
    queryRequestBuilder(
      partitionKeyValue,
      queryOrder,
      limit,
      returnConsumedCapacity,
      overrideConfiguration
    ).build()
  }

  protected def queryRequestBuilder(partitionKeyValue: K,
                                    queryOrder: QueryOrder = Asc,
                                    limit: Option[Int] = None,
                                    returnConsumedCapacity: Option[ReturnConsumedCapacity] = None,
                                    overrideConfiguration: Option[AwsRequestOverrideConfiguration] = None): QueryRequest.Builder = {
    val builder = QueryRequest.builder()
      .keyConditionExpression("#pkName = :pkValue")
      //      .expressionAttributeNames(Map("#pkName" -> partitionKey.name).asJava)
      //      .expressionAttributeValues(Map(":pkValue" -> partitionKey.converter.toAttribute(partitionKeyValue)).asJava)
      .select(Select.ALL_ATTRIBUTES)
      .tableName(tableName)
      .scanIndexForward(if (queryOrder == Asc) true else false)

    val builderWithCapacity = returnConsumedCapacity.map(builder.returnConsumedCapacity).getOrElse(builder)
    val builderWithConfiguration = overrideConfiguration.map(builderWithCapacity.overrideConfiguration).getOrElse(builderWithCapacity)
    val builderWithLimit = limit.map(builderWithConfiguration.limit(_)).getOrElse(builderWithConfiguration)

    builderWithLimit
  }

  def putItemRequest(): PutItemRequest = {

    PutItemRequest.builder()

      .build()

    null
  }

  def bla(filterExpression: SortKeyConditionExpression[_]): Unit = {

  }
}


abstract class SortedDynamoTable[T: ItemConverter, K: AttributeConverter, S: AttributeConverter](tableName: String)
  extends DynamoTable[T, K](tableName) {

  val sortKey: SortKey[S]

  // todo
  protected def sortAttribute(f: T => S): SortKey[S] = SortKey[S]("fake_name")


  def getItemRequest(partitionKey: K, sortKey: S): GetItemRequest = {
    GetItemRequest.builder()

      //      .key()
      .build()
    null
  }


  def queryRequest(partitionKeyValue: K,
                   sortKeyExpression: SortKeyConditionExpression[S]): QueryRequest = {
    QueryRequest.builder()

      .build()
    null
  }


}