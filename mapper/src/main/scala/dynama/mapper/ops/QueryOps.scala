package dynama.mapper.ops

import dynama.mapper.Aliases.EvaluatedKey
import dynama.mapper.SortKey.SortKeyOps
import dynama.mapper.{Asc, DynamoTable, PartitionKey, QueryOrder, SortKeyConditionExpression, SortedDynamoTable}
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration
import software.amazon.awssdk.services.dynamodb.model.{QueryRequest, ReturnConsumedCapacity, Select}

import scala.collection.JavaConverters._

trait QueryOps extends BaseOps {

  protected def queryRequest(evaluatedExpression: EvaluatedKey,
                             queryOrder: QueryOrder,
                             limit: Option[Int],
                             returnConsumedCapacity: Option[ReturnConsumedCapacity],
                             overrideConfiguration: Option[AwsRequestOverrideConfiguration]): QueryRequest = {
    val builder = QueryRequest.builder()
      .keyConditionExpression(evaluatedExpression._1)
      .expressionAttributeNames(evaluatedExpression._2.asJava)
      .expressionAttributeValues(evaluatedExpression._3.asJava)
      .select(Select.ALL_ATTRIBUTES)
      .tableName(tableName)
      .scanIndexForward(if (queryOrder == Asc) true else false)

    val builderWithCapacity = returnConsumedCapacity.map(builder.returnConsumedCapacity).getOrElse(builder)
    val builderWithConfiguration = overrideConfiguration.map(builderWithCapacity.overrideConfiguration).getOrElse(builderWithCapacity)
    val builderWithLimit = limit.map(builderWithConfiguration.limit(_)).getOrElse(builderWithConfiguration)

    builderWithLimit.build()
  }

}

trait SimpleQueryOps[K] extends QueryOps {
  this: DynamoTable[_, K] =>

  def queryRequest(partitionKeyValue: K,
                   queryOrder: QueryOrder = Asc,
                   limit: Option[Int] = None,
                   returnConsumedCapacity: Option[ReturnConsumedCapacity] = None,
                   overrideConfiguration: Option[AwsRequestOverrideConfiguration] = None): QueryRequest = {
    queryRequest(
      PartitionKey.evaluate(partitionKey, partitionKeyValue),
      queryOrder,
      limit,
      returnConsumedCapacity,
      overrideConfiguration
    )
  }

}

trait SortedQueryOps[K, S] extends QueryOps {
  this: SortedDynamoTable[_, K, S] =>

  def queryRequest(partitionKeyValue: K,
                   queryOrder: QueryOrder = Asc,
                   limit: Option[Int] = None,
                   returnConsumedCapacity: Option[ReturnConsumedCapacity] = None,
                   overrideConfiguration: Option[AwsRequestOverrideConfiguration] = None): QueryRequest = {
    queryRequest(
      PartitionKey.evaluate(partitionKey, partitionKeyValue),
      queryOrder,
      limit,
      returnConsumedCapacity,
      overrideConfiguration
    )
  }

  def queryRequestWithSortKey(partitionKeyValue: K,
                              sortKeyExpression: SortKeyConditionExpression[S],
                              queryOrder: QueryOrder = Asc,
                              limit: Option[Int] = None,
                              returnConsumedCapacity: Option[ReturnConsumedCapacity] = None,
                              overrideConfiguration: Option[AwsRequestOverrideConfiguration] = None): QueryRequest = {
    val partitionKeyAttributes = PartitionKey.evaluate(partitionKey, partitionKeyValue)
    val sortKeyAttributes = SortKeyConditionExpression.evaluate(sortKeyExpression)
    val evaluatedKey: EvaluatedKey = (
      s"${partitionKeyAttributes._1} and ${sortKeyAttributes._2}",
      partitionKeyAttributes._2 ++ sortKeyAttributes._2,
      partitionKeyAttributes._3 ++ sortKeyAttributes._3
    )
    queryRequest(
      evaluatedKey,
      queryOrder,
      limit,
      returnConsumedCapacity,
      overrideConfiguration
    )
  }

}
