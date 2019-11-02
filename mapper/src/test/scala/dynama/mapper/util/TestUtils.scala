package dynama.mapper.util

import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration

object TestUtils {

  val ConfigurationOverride: AwsRequestOverrideConfiguration =
    AwsRequestOverrideConfiguration.builder().putHeader("customHeader", "customHeaderValue").build()

}
