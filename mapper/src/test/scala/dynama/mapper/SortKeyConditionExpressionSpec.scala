package dynama.mapper

import org.scalatest.{FlatSpec, Matchers}
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

class SortKeyConditionExpressionSpec extends FlatSpec with Matchers {

  val attribute1: SortKey[Int] = SortKey[Int]("attribute1")
  val attribute3: SortKey[String] = SortKey[String]("attribute3")

  "Equals condition expression" should "be serializable" in {
    val expression = Equals(attribute1, 10)

    val (expressionStr, keyMappings, valueMappings) = SortKeyConditionExpression.evaluate(expression)

    expressionStr should be("#attribute1Name = :attribute1Value")
    keyMappings should be(Map("#attribute1Name" -> "attribute1"))
    valueMappings should be(Map(":attribute1Value" -> AttributeValue.builder().n("10").build()))
  }

  "Less than condition expression" should "be serializable" in {
    val expression = attribute1 < 10

    val (expressionStr, keyMappings, valueMappings) = SortKeyConditionExpression.evaluate(expression)

    expressionStr should be("#attribute1Name < :attribute1Value")
    keyMappings should be(Map("#attribute1Name" -> "attribute1"))
    valueMappings should be(Map(":attribute1Value" -> AttributeValue.builder().n("10").build()))
  }

  "Less than or equal condition expression" should "be serializable" in {
    val expression = attribute1 <= 10

    val (expressionStr, keyMappings, valueMappings) = SortKeyConditionExpression.evaluate(expression)

    expressionStr should be("#attribute1Name <= :attribute1Value")
    keyMappings should be(Map("#attribute1Name" -> "attribute1"))
    valueMappings should be(Map(":attribute1Value" -> AttributeValue.builder().n("10").build()))
  }

  "Bigger than condition expression" should "be serializable" in {
    val expression = attribute1 > 10

    val (expressionStr, keyMappings, valueMappings) = SortKeyConditionExpression.evaluate(expression)

    expressionStr should be("#attribute1Name > :attribute1Value")
    keyMappings should be(Map("#attribute1Name" -> "attribute1"))
    valueMappings should be(Map(":attribute1Value" -> AttributeValue.builder().n("10").build()))
  }

  "Bigger than or equal condition expression" should "be serializable" in {
    val expression = attribute1 >= 10

    val (expressionStr, keyMappings, valueMappings) = SortKeyConditionExpression.evaluate(expression)

    expressionStr should be("#attribute1Name >= :attribute1Value")
    keyMappings should be(Map("#attribute1Name" -> "attribute1"))
    valueMappings should be(Map(":attribute1Value" -> AttributeValue.builder().n("10").build()))
  }

  "Between condition expression" should "be serializable" in {
    val expression = attribute1 between 10 -> 20

    val (expressionStr, keyMappings, valueMappings) = SortKeyConditionExpression.evaluate(expression)

    expressionStr should be("#attribute1Name BETWEEN :attribute1Value1 AND :attribute1Value2")
    keyMappings should be(Map("#attribute1Name" -> "attribute1"))
    valueMappings should be(Map(
      ":attribute1Value1" -> AttributeValue.builder().n("10").build(),
      ":attribute1Value2" -> AttributeValue.builder().n("20").build()
    ))
  }

  "Begins with condition expression" should "be serializable" in {
    val expression = attribute3 beginsWith "dict."

    val (expressionStr, keyMappings, valueMappings) = SortKeyConditionExpression.evaluate(expression)

    expressionStr should be("begins_with (#attribute3Name, :attribute3Value)")
    keyMappings should be(Map("#attribute3Name" -> "attribute3"))
    valueMappings should be(Map(":attribute3Value" -> AttributeValue.builder().s("dict.").build()))
  }

}
