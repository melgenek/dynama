package dynama.mapper.ops

import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest

trait DeleteTableOps extends BaseOps {

  def deleteTableRequest(): DeleteTableRequest = {
    DeleteTableRequest.builder().tableName(tableName).build()
  }

  def deleteTableRequestWithConfiguration(overrideConfiguration: AwsRequestOverrideConfiguration): DeleteTableRequest = {
    DeleteTableRequest.builder()
      .tableName(tableName)
      .overrideConfiguration(overrideConfiguration)
      .build()
  }

}
