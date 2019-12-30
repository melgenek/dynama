package dynama.converter

import dynama.converter.Attribute.{Flat, Simple}
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

import scala.reflect.ClassTag

trait CompositeConverter[T] {
  def decode(map: Map[String, AttributeValue]): DecodingResult[T]

  def encode(value: T): Map[String, AttributeValue]
}

object CompositeConverter {

  def apply[T]: CompositeConverterBuilder[T] = new CompositeConverterBuilder[T]()

}

class CompositeConverterBuilder[T] {

  def chooseBy[A: SimpleConverter,
    T1 <: T : ClassTag,
    T2 <: T : ClassTag](attributeName: String, defaultValue: A)
                       (option1: (A, CompositeConverter[T1]),
                        option2: (A, CompositeConverter[T2])): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        map.get(attributeName)
          .map(implicitly[SimpleConverter[A]].decode)
          .map(_.left.map(DecodingError(s"The attribute '$attributeName' cannot be decoded", _)))
          .getOrElse(Right(defaultValue))
          .flatMap {
            case option1._1 => option1._2.decode(map)
            case option2._1 => option2._2.decode(map)
            case another => Left(DecodingError(s"The attribute '$attributeName' has an unknown value '$another'"))
          }
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        value match {
          case v: T1 => option1._2.encode(v)
          case v: T2 => option2._2.encode(v)
        }
      }
    }
  }

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
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value)
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
        encodeAttribute(attribute1, value) ++
          encodeAttribute(attribute2, value) ++
          encodeAttribute(attribute3, value)
      }
    }
  }

  def apply[A1, A2, A3, A4](attribute1: Attribute[T, A1],
                            attribute2: Attribute[T, A2],
                            attribute3: Attribute[T, A3],
                            attribute4: Attribute[T, A4])
                           (constructor: (A1, A2, A3, A4) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
        } yield constructor(value1, value2, value3, value4)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++
          encodeAttribute(attribute2, value) ++
          encodeAttribute(attribute3, value) ++
          encodeAttribute(attribute4, value)
      }
    }
  }

  private def decodeAttribute[A](attribute: Attribute[T, A], map: Map[String, AttributeValue]): DecodingResult[A] = {
    attribute match {
      case a@Simple(name, _) =>
        a.converter.decode(map.getOrElse(name, NulAttributeValue))
          .left.map(DecodingError(s"The attribute '$name' cannot be decoded", _))
      case a: Flat[_, _] => a.converter.decode(map)
    }
  }

  private def encodeAttribute[A](attribute: Attribute[T, A], value: T): Map[String, AttributeValue] = {
    attribute match {
      case a@Simple(name, get) => Map(name -> a.converter.encode(get(value)))
      case a@Flat(get) => a.converter.encode(get(value))
    }
  }

}
