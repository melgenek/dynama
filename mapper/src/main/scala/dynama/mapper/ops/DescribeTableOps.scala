package dynama.mapper.ops

import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest

trait DescribeTableOps extends BaseOps {

  def describeTableRequest(): DescribeTableRequest = {
    DescribeTableRequest.builder()
      .tableName(tableName)
      .build()
  }

  def describeTableRequestWithConfiguration(overrideConfiguration: AwsRequestOverrideConfiguration): DescribeTableRequest = {
    DescribeTableRequest.builder()
      .overrideConfiguration(overrideConfiguration)
      .tableName(tableName)
      .build()
  }

}
