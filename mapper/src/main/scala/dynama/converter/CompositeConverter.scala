package dynama.converter

import dynama.converter.Attribute.{Composite, Simple}
import dynama.converter.CompositeConverter.{decodeAttribute, encodeAttribute}
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

trait CompositeConverter[T] {
  def decode(map: Map[String, AttributeValue]): DecodingResult[T]

  def encode(value: T): Map[String, AttributeValue]
}

object CompositeConverter {

  implicit val UnitConverter: CompositeConverter[Unit] = new CompositeConverter[Unit] {
    override def decode(map: Map[String, AttributeValue]) = Right(())

    override def encode(value: Unit) = Map.empty
  }

  def apply[T]: CompositeConverterBuilder[T] = new CompositeConverterBuilder[T]()

  private[converter] def decodeAttribute[T, A](attribute: Attribute[T, A], map: Map[String, AttributeValue]): DecodingResult[A] = {
    attribute match {
      case a@Simple(name, _) =>
        map.get(name)
          .map(a.converter.decode)
          .getOrElse(Left(DecodingError(s"There is no attribute '$name'")))
      case a@Composite(_) => a.converter.decode(map)
    }
  }

  private[converter] def encodeAttribute[T, A](attribute: Attribute[T, A], value: T): Map[String, AttributeValue] = {
    attribute match {
      case a@Simple(name, get) => Map(name -> a.converter.encode(get(value)))
      case a@Composite(get) => a.converter.encode(get(value))
    }
  }

}

class CompositeConverterBuilder[T] {

  def apply[A1](attribute1: Attribute[T, A1])
               (constructor: A1 => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
        } yield constructor(value1)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value)
      }
    }
  }

  def apply[A1, A2](attribute1: Attribute[T, A1],
                    attribute2: Attribute[T, A2])
                   (constructor: (A1, A2) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
        } yield constructor(value1, value2)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        val map1 = encodeAttribute(attribute1, value)
        val map2 = encodeAttribute(attribute2, value)
        map1 ++ map2
      }
    }
  }

  def apply[A1, A2, A3](attribute1: Attribute[T, A1],
                        attribute2: Attribute[T, A2],
                        attribute3: Attribute[T, A3])
                       (constructor: (A1, A2, A3) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
        } yield constructor(value1, value2, value3)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        val map1 = encodeAttribute(attribute1, value)
        val map2 = encodeAttribute(attribute2, value)
        val map3 = encodeAttribute(attribute3, value)
        map1 ++ map2 ++ map3
      }
    }
  }

}
