package dynama.mapper.ops

import dynama.mapper.{DynamoTable, SortedDynamoTable}
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration
import software.amazon.awssdk.services.dynamodb.model.{AttributeValue, GetItemRequest}

import scala.collection.JavaConverters._

trait GetItemOps extends BaseOps {

  protected def getItemRequest(keys: Map[String, AttributeValue],
                               consistentRead: Boolean,
                               overrideConfiguration: Option[AwsRequestOverrideConfiguration]): GetItemRequest = {
    val builder = GetItemRequest.builder()
      .key(keys.asJava)
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
      Map(partitionKey.name -> partitionKey.converter.toAttribute(partitionKeyValue)),
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
        partitionKey.name -> partitionKey.converter.toAttribute(partitionKeyValue),
        sortKey.name -> sortKey.converter.toAttribute(sortKeyValue)
      ),
      consistentRead,
      overrideConfiguration
    )
  }

}
