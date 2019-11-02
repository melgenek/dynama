package dynama.mapper

import dynama.mapper.Aliases.{AttributeNames, AttributeValues, EvaluatedKey, Expression}

// https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Query.html#Query.KeyConditionExpressions
// https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Expressions.OperatorsAndFunctions.html
sealed trait SortKeyConditionExpression[A] {

  protected def evaluateWithPrefix(prefix: String): EvaluatedKey

  protected def simpleExpression(prefix: String, key: SortKey[A], value: A, operator: String): (Expression, AttributeNames, AttributeValues) = {
    val attributeName = s"#$prefix${key.name}Name"
    val attributeValue = s":$prefix${key.name}Value"
    val expression = s"$attributeName $operator $attributeValue"
    val attributeNames = Map(attributeName -> key.name)
    val attributeValues = Map(attributeValue -> key.converter.toAttribute(value))
    (expression, attributeNames, attributeValues)
  }

}

object SortKeyConditionExpression {

  def evaluate[A](expression: SortKeyConditionExpression[A]): EvaluatedKey = {
    expression.evaluateWithPrefix("")
  }

}

case class Equals[A](key: SortKey[A], value: A) extends SortKeyConditionExpression[A] {
  override protected def evaluateWithPrefix(prefix: String): (Expression, AttributeNames, AttributeValues) =
    simpleExpression(prefix, key, value, "=")
}

case class LessThan[A](key: SortKey[A], value: A) extends SortKeyConditionExpression[A] {
  override protected def evaluateWithPrefix(prefix: String): (Expression, AttributeNames, AttributeValues) =
    simpleExpression(prefix, key, value, "<")
}

case class LessThanOrEqual[A](key: SortKey[A], value: A) extends SortKeyConditionExpression[A] {
  override protected def evaluateWithPrefix(prefix: String): (Expression, AttributeNames, AttributeValues) =
    simpleExpression(prefix, key, value, "<=")
}

case class BiggerThan[A](key: SortKey[A], value: A) extends SortKeyConditionExpression[A] {
  override protected def evaluateWithPrefix(prefix: String): (Expression, AttributeNames, AttributeValues) =
    simpleExpression(prefix, key, value, ">")
}

case class BiggerThanOrEqual[A](key: SortKey[A], value: A) extends SortKeyConditionExpression[A] {
  override protected def evaluateWithPrefix(prefix: String): (Expression, AttributeNames, AttributeValues) =
    simpleExpression(prefix, key, value, ">=")
}

case class BeginsWith(key: SortKey[String], value: String) extends SortKeyConditionExpression[String] {
  override protected def evaluateWithPrefix(prefix: String): (Expression, AttributeNames, AttributeValues) = {
    val attributeName = s"#$prefix${key.name}Name"
    val attributeValue = s":$prefix${key.name}Value"
    val expression = s"begins_with ($attributeName, $attributeValue)"
    val attributeNames = Map(attributeName -> key.name)
    val attributeValues = Map(attributeValue -> key.converter.toAttribute(value))
    (expression, attributeNames, attributeValues)
  }
}

case class Between[A](key: SortKey[A], value1: A, value2: A) extends SortKeyConditionExpression[A] {
  override protected def evaluateWithPrefix(prefix: String): (Expression, AttributeNames, AttributeValues) = {
    val attributeName = s"#$prefix${key.name}Name"
    val attributeValue1 = s":$prefix${key.name}Value1"
    val attributeValue2 = s":$prefix${key.name}Value2"
    val expression = s"$attributeName BETWEEN $attributeValue1 AND $attributeValue2"
    val attributeNames = Map(attributeName -> key.name)
    val attributeValues = Map(
      attributeValue1 -> key.converter.toAttribute(value1),
      attributeValue2 -> key.converter.toAttribute(value2)
    )
    (expression, attributeNames, attributeValues)
  }
}
