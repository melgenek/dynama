package dynama.mapper

import java.time.Instant

import dynama.mapper.KeyDefinition.{CompositeKeyDefinition, PartitionKeyDefinition}
import org.scalatest.{FlatSpec, Matchers}

class KeyDefinitionSpec extends FlatSpec with Matchers {

  case class SimpleClass(field1: Int, field2: Instant)

  case class SimpleClassWithRenamedField(@DynamoAttribute("renamedField") field1: String, `field-2`: Instant)

  implicit val instantConverter: AttributeConverter[Instant] = AttributeConverter.StringConverter.imap(_.toString, Instant.parse)

  "KeyDefinition" should "derive partition key" in {
    val keyDefinition = KeyDefinition.partitionKey[SimpleClass](_.field1)

    keyDefinition should be(PartitionKeyDefinition("field1", AttributeConverter.IntConverter))
  }

  it should "derive composite key" in {
    val keyDefinition = KeyDefinition.compositeKey[SimpleClass](_.field1, _.field2)

    keyDefinition should be(CompositeKeyDefinition("field1", AttributeConverter.IntConverter, "field2", instantConverter))
  }

  it should "derive key with renamed attribute" in {
    val keyDefinition = KeyDefinition.compositeKey[SimpleClassWithRenamedField](_.`field-2`, _.field1)

    keyDefinition should be(CompositeKeyDefinition("field-2", instantConverter, "renamedField", AttributeConverter.StringConverter))
  }

}
