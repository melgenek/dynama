package dynama.mapper

import software.amazon.awssdk.services.dynamodb.model.AttributeValue

object Aliases {

  type Expression = String
  type AttributeNames = Map[String, String]
  type AttributeValues = Map[String, AttributeValue]

  type EvaluatedKey = (Expression, AttributeNames, AttributeValues)

}
