package dynama.mapper

import software.amazon.awssdk.services.dynamodb.model.AttributeValue

// https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Query.html#Query.KeyConditionExpressions
// https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Expressions.OperatorsAndFunctions.html
sealed trait SortKeyConditionExpression[A] {

  type Expression = String
  type AttributeNames = Map[String, String]
  type AttributeValues = Map[String, AttributeValue]

  protected[mapper] def evaluate: (Expression, AttributeNames, AttributeValues) = evaluateWithPrefix("")

  protected[mapper] def evaluateWithPrefix(prefix: String): (Expression, AttributeNames, AttributeValues)

  protected def simpleExpression(prefix: String, attribute: SortKey[A], value: A, operator: String): (Expression, AttributeNames, AttributeValues) = {
    val attributeName = s"#$prefix${attribute.name}Name"
    val attributeValue = s":$prefix${attribute.name}Value"
    val expression = s"$attributeName $operator $attributeValue"
    val attributeNames = Map(attributeName -> attribute.name)
    val attributeValues = Map(attributeValue -> attribute.converter.toAttribute(value))
    (expression, attributeNames, attributeValues)
  }

}

case class Equals[A](attribute: SortKey[A], value: A) extends SortKeyConditionExpression[A] {
  override protected[mapper] def evaluateWithPrefix(prefix: String): (Expression, AttributeNames, AttributeValues) =
    simpleExpression(prefix, attribute, value, "=")
}

case class LessThan[A](attribute: SortKey[A], value: A) extends SortKeyConditionExpression[A] {
  override protected[mapper] def evaluateWithPrefix(prefix: String): (Expression, AttributeNames, AttributeValues) =
    simpleExpression(prefix, attribute, value, "<")
}

case class LessThanOrEqual[A](attribute: SortKey[A], value: A) extends SortKeyConditionExpression[A] {
  override protected[mapper] def evaluateWithPrefix(prefix: String): (Expression, AttributeNames, AttributeValues) =
    simpleExpression(prefix, attribute, value, "<=")
}

case class BiggerThan[A](attribute: SortKey[A], value: A) extends SortKeyConditionExpression[A] {
  override protected[mapper] def evaluateWithPrefix(prefix: String): (Expression, AttributeNames, AttributeValues) =
    simpleExpression(prefix, attribute, value, ">")
}

case class BiggerThanOrEqual[A](attribute: SortKey[A], value: A) extends SortKeyConditionExpression[A] {
  override protected[mapper] def evaluateWithPrefix(prefix: String): (Expression, AttributeNames, AttributeValues) =
    simpleExpression(prefix, attribute, value, ">=")
}

case class BeginsWith(attribute: SortKey[String], value: String) extends SortKeyConditionExpression[String] {
  override protected[mapper] def evaluateWithPrefix(prefix: String): (Expression, AttributeNames, AttributeValues) = {
    val attributeName = s"#$prefix${attribute.name}Name"
    val attributeValue = s":$prefix${attribute.name}Value"
    val expression = s"begins_with ($attributeName, $attributeValue)"
    val attributeNames = Map(attributeName -> attribute.name)
    val attributeValues = Map(attributeValue -> attribute.converter.toAttribute(value))
    (expression, attributeNames, attributeValues)
  }
}

case class Between[A](attribute: SortKey[A], value1: A, value2: A) extends SortKeyConditionExpression[A] {
  override protected[mapper] def evaluateWithPrefix(prefix: String): (Expression, AttributeNames, AttributeValues) = {
    val attributeName = s"#$prefix${attribute.name}Name"
    val attributeValue1 = s":$prefix${attribute.name}Value1"
    val attributeValue2 = s":$prefix${attribute.name}Value2"
    val expression = s"$attributeName BETWEEN $attributeValue1 AND $attributeValue2"
    val attributeNames = Map(attributeName -> attribute.name)
    val attributeValues = Map(
      attributeValue1 -> attribute.converter.toAttribute(value1),
      attributeValue2 -> attribute.converter.toAttribute(value2)
    )
    (expression, attributeNames, attributeValues)
  }
}
