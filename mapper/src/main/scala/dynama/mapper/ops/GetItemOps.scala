package dynama.mapper.ops

import dynama.mapper.{DynamoTable, SortedDynamoTable}
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration
import software.amazon.awssdk.services.dynamodb.model.{AttributeValue, GetItemRequest}

import scala.collection.JavaConverters._

trait GetItemOps extends BaseOps {

  protected def getItemRequest(keys: Map[String, AttributeValue],
                               keyNames: Map[String, String],
                               consistentRead: Boolean,
                               overrideConfiguration: Option[AwsRequestOverrideConfiguration]): GetItemRequest = {
    val builder = GetItemRequest.builder()
      .key(keys.asJava)
      .expressionAttributeNames(keyNames.asJava)
      .consistentRead(consistentRead)
      .tableName(tableName)

    val builderWithConfiguration = overrideConfiguration.map(builder.overrideConfiguration).getOrElse(builder)

    builderWithConfiguration.build()
  }

}

trait SimpleGetItemOps[K] extends GetItemOps {
  this: DynamoTable[_, K] =>

  def getItemRequest(partitionKeyValue: K,
                     consistentRead: Boolean = false,
                     overrideConfiguration: Option[AwsRequestOverrideConfiguration] = None): GetItemRequest = {
    getItemRequest(
      Map("#pkName" -> partitionKey.converter.toAttribute(partitionKeyValue)),
      Map("#pkName" -> partitionKey.name),
      consistentRead,
      overrideConfiguration
    )
  }

}

trait SortedGetItemOps[K, S] extends GetItemOps {
  this: SortedDynamoTable[_, K, S] =>


  def getItemRequest(partitionKeyValue: K,
                     sortKeyValue: S,
                     consistentRead: Boolean = false,
                     overrideConfiguration: Option[AwsRequestOverrideConfiguration] = None): GetItemRequest = {
    getItemRequest(
      Map(
        "#pkName" -> partitionKey.converter.toAttribute(partitionKeyValue),
        "#skName" -> sortKey.converter.toAttribute(sortKeyValue)
      ),
      Map(
        "#pkName" -> partitionKey.name,
        "#skName" -> sortKey.name
      ),
      consistentRead,
      overrideConfiguration
    )
  }

}
