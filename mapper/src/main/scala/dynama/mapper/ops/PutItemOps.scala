package dynama.mapper.ops

import dynama.mapper.ItemWriter
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest

import scala.collection.JavaConverters._

trait PutItemOps[T] extends BaseOps {

  val writer: ItemWriter[T]

  def putItemRequest(item: T,
                     overrideConfiguration: Option[AwsRequestOverrideConfiguration]): PutItemRequest = {
    val builder = PutItemRequest.builder()
      .item(writer.toMap(item).asJava)
      .tableName(tableName)

    val builderWithConfiguration = overrideConfiguration.map(builder.overrideConfiguration).getOrElse(builder)

    builderWithConfiguration.build()
  }

}
