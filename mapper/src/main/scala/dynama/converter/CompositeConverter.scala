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

  def chooseBy[A: SimpleConverter](attributeName: String, defaultValue: A) =
    new CompositeConverterWithOptionsBuilder[T, A](attributeName, defaultValue)

  def apply[A1]
  (
    attribute1: Attribute[T, A1]
  )
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


  def apply[A1, A2]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2]
  )
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


  def apply[A1, A2, A3]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3]
  )
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
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value)
      }
    }
  }


  def apply[A1, A2, A3, A4]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4]
  )
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
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5]
  )
  (constructor: (A1, A2, A3, A4, A5) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
        } yield constructor(value1, value2, value3, value4, value5)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5, A6]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5],
    attribute6: Attribute[T, A6]
  )
  (constructor: (A1, A2, A3, A4, A5, A6) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
          value6 <- decodeAttribute(attribute6, map)
        } yield constructor(value1, value2, value3, value4, value5, value6)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value) ++ encodeAttribute(attribute6, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5, A6, A7]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5],
    attribute6: Attribute[T, A6],
    attribute7: Attribute[T, A7]
  )
  (constructor: (A1, A2, A3, A4, A5, A6, A7) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
          value6 <- decodeAttribute(attribute6, map)
          value7 <- decodeAttribute(attribute7, map)
        } yield constructor(value1, value2, value3, value4, value5, value6, value7)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value) ++ encodeAttribute(attribute6, value) ++ encodeAttribute(attribute7, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5, A6, A7, A8]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5],
    attribute6: Attribute[T, A6],
    attribute7: Attribute[T, A7],
    attribute8: Attribute[T, A8]
  )
  (constructor: (A1, A2, A3, A4, A5, A6, A7, A8) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
          value6 <- decodeAttribute(attribute6, map)
          value7 <- decodeAttribute(attribute7, map)
          value8 <- decodeAttribute(attribute8, map)
        } yield constructor(value1, value2, value3, value4, value5, value6, value7, value8)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value) ++ encodeAttribute(attribute6, value) ++ encodeAttribute(attribute7, value) ++ encodeAttribute(attribute8, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5, A6, A7, A8, A9]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5],
    attribute6: Attribute[T, A6],
    attribute7: Attribute[T, A7],
    attribute8: Attribute[T, A8],
    attribute9: Attribute[T, A9]
  )
  (constructor: (A1, A2, A3, A4, A5, A6, A7, A8, A9) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
          value6 <- decodeAttribute(attribute6, map)
          value7 <- decodeAttribute(attribute7, map)
          value8 <- decodeAttribute(attribute8, map)
          value9 <- decodeAttribute(attribute9, map)
        } yield constructor(value1, value2, value3, value4, value5, value6, value7, value8, value9)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value) ++ encodeAttribute(attribute6, value) ++ encodeAttribute(attribute7, value) ++ encodeAttribute(attribute8, value) ++ encodeAttribute(attribute9, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5],
    attribute6: Attribute[T, A6],
    attribute7: Attribute[T, A7],
    attribute8: Attribute[T, A8],
    attribute9: Attribute[T, A9],
    attribute10: Attribute[T, A10]
  )
  (constructor: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
          value6 <- decodeAttribute(attribute6, map)
          value7 <- decodeAttribute(attribute7, map)
          value8 <- decodeAttribute(attribute8, map)
          value9 <- decodeAttribute(attribute9, map)
          value10 <- decodeAttribute(attribute10, map)
        } yield constructor(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value) ++ encodeAttribute(attribute6, value) ++ encodeAttribute(attribute7, value) ++ encodeAttribute(attribute8, value) ++ encodeAttribute(attribute9, value) ++ encodeAttribute(attribute10, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5],
    attribute6: Attribute[T, A6],
    attribute7: Attribute[T, A7],
    attribute8: Attribute[T, A8],
    attribute9: Attribute[T, A9],
    attribute10: Attribute[T, A10],
    attribute11: Attribute[T, A11]
  )
  (constructor: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
          value6 <- decodeAttribute(attribute6, map)
          value7 <- decodeAttribute(attribute7, map)
          value8 <- decodeAttribute(attribute8, map)
          value9 <- decodeAttribute(attribute9, map)
          value10 <- decodeAttribute(attribute10, map)
          value11 <- decodeAttribute(attribute11, map)
        } yield constructor(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value) ++ encodeAttribute(attribute6, value) ++ encodeAttribute(attribute7, value) ++ encodeAttribute(attribute8, value) ++ encodeAttribute(attribute9, value) ++ encodeAttribute(attribute10, value) ++ encodeAttribute(attribute11, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5],
    attribute6: Attribute[T, A6],
    attribute7: Attribute[T, A7],
    attribute8: Attribute[T, A8],
    attribute9: Attribute[T, A9],
    attribute10: Attribute[T, A10],
    attribute11: Attribute[T, A11],
    attribute12: Attribute[T, A12]
  )
  (constructor: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
          value6 <- decodeAttribute(attribute6, map)
          value7 <- decodeAttribute(attribute7, map)
          value8 <- decodeAttribute(attribute8, map)
          value9 <- decodeAttribute(attribute9, map)
          value10 <- decodeAttribute(attribute10, map)
          value11 <- decodeAttribute(attribute11, map)
          value12 <- decodeAttribute(attribute12, map)
        } yield constructor(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value) ++ encodeAttribute(attribute6, value) ++ encodeAttribute(attribute7, value) ++ encodeAttribute(attribute8, value) ++ encodeAttribute(attribute9, value) ++ encodeAttribute(attribute10, value) ++ encodeAttribute(attribute11, value) ++ encodeAttribute(attribute12, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5],
    attribute6: Attribute[T, A6],
    attribute7: Attribute[T, A7],
    attribute8: Attribute[T, A8],
    attribute9: Attribute[T, A9],
    attribute10: Attribute[T, A10],
    attribute11: Attribute[T, A11],
    attribute12: Attribute[T, A12],
    attribute13: Attribute[T, A13]
  )
  (constructor: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
          value6 <- decodeAttribute(attribute6, map)
          value7 <- decodeAttribute(attribute7, map)
          value8 <- decodeAttribute(attribute8, map)
          value9 <- decodeAttribute(attribute9, map)
          value10 <- decodeAttribute(attribute10, map)
          value11 <- decodeAttribute(attribute11, map)
          value12 <- decodeAttribute(attribute12, map)
          value13 <- decodeAttribute(attribute13, map)
        } yield constructor(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value) ++ encodeAttribute(attribute6, value) ++ encodeAttribute(attribute7, value) ++ encodeAttribute(attribute8, value) ++ encodeAttribute(attribute9, value) ++ encodeAttribute(attribute10, value) ++ encodeAttribute(attribute11, value) ++ encodeAttribute(attribute12, value) ++ encodeAttribute(attribute13, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5],
    attribute6: Attribute[T, A6],
    attribute7: Attribute[T, A7],
    attribute8: Attribute[T, A8],
    attribute9: Attribute[T, A9],
    attribute10: Attribute[T, A10],
    attribute11: Attribute[T, A11],
    attribute12: Attribute[T, A12],
    attribute13: Attribute[T, A13],
    attribute14: Attribute[T, A14]
  )
  (constructor: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
          value6 <- decodeAttribute(attribute6, map)
          value7 <- decodeAttribute(attribute7, map)
          value8 <- decodeAttribute(attribute8, map)
          value9 <- decodeAttribute(attribute9, map)
          value10 <- decodeAttribute(attribute10, map)
          value11 <- decodeAttribute(attribute11, map)
          value12 <- decodeAttribute(attribute12, map)
          value13 <- decodeAttribute(attribute13, map)
          value14 <- decodeAttribute(attribute14, map)
        } yield constructor(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value) ++ encodeAttribute(attribute6, value) ++ encodeAttribute(attribute7, value) ++ encodeAttribute(attribute8, value) ++ encodeAttribute(attribute9, value) ++ encodeAttribute(attribute10, value) ++ encodeAttribute(attribute11, value) ++ encodeAttribute(attribute12, value) ++ encodeAttribute(attribute13, value) ++ encodeAttribute(attribute14, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5],
    attribute6: Attribute[T, A6],
    attribute7: Attribute[T, A7],
    attribute8: Attribute[T, A8],
    attribute9: Attribute[T, A9],
    attribute10: Attribute[T, A10],
    attribute11: Attribute[T, A11],
    attribute12: Attribute[T, A12],
    attribute13: Attribute[T, A13],
    attribute14: Attribute[T, A14],
    attribute15: Attribute[T, A15]
  )
  (constructor: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
          value6 <- decodeAttribute(attribute6, map)
          value7 <- decodeAttribute(attribute7, map)
          value8 <- decodeAttribute(attribute8, map)
          value9 <- decodeAttribute(attribute9, map)
          value10 <- decodeAttribute(attribute10, map)
          value11 <- decodeAttribute(attribute11, map)
          value12 <- decodeAttribute(attribute12, map)
          value13 <- decodeAttribute(attribute13, map)
          value14 <- decodeAttribute(attribute14, map)
          value15 <- decodeAttribute(attribute15, map)
        } yield constructor(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value) ++ encodeAttribute(attribute6, value) ++ encodeAttribute(attribute7, value) ++ encodeAttribute(attribute8, value) ++ encodeAttribute(attribute9, value) ++ encodeAttribute(attribute10, value) ++ encodeAttribute(attribute11, value) ++ encodeAttribute(attribute12, value) ++ encodeAttribute(attribute13, value) ++ encodeAttribute(attribute14, value) ++ encodeAttribute(attribute15, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5],
    attribute6: Attribute[T, A6],
    attribute7: Attribute[T, A7],
    attribute8: Attribute[T, A8],
    attribute9: Attribute[T, A9],
    attribute10: Attribute[T, A10],
    attribute11: Attribute[T, A11],
    attribute12: Attribute[T, A12],
    attribute13: Attribute[T, A13],
    attribute14: Attribute[T, A14],
    attribute15: Attribute[T, A15],
    attribute16: Attribute[T, A16]
  )
  (constructor: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
          value6 <- decodeAttribute(attribute6, map)
          value7 <- decodeAttribute(attribute7, map)
          value8 <- decodeAttribute(attribute8, map)
          value9 <- decodeAttribute(attribute9, map)
          value10 <- decodeAttribute(attribute10, map)
          value11 <- decodeAttribute(attribute11, map)
          value12 <- decodeAttribute(attribute12, map)
          value13 <- decodeAttribute(attribute13, map)
          value14 <- decodeAttribute(attribute14, map)
          value15 <- decodeAttribute(attribute15, map)
          value16 <- decodeAttribute(attribute16, map)
        } yield constructor(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value) ++ encodeAttribute(attribute6, value) ++ encodeAttribute(attribute7, value) ++ encodeAttribute(attribute8, value) ++ encodeAttribute(attribute9, value) ++ encodeAttribute(attribute10, value) ++ encodeAttribute(attribute11, value) ++ encodeAttribute(attribute12, value) ++ encodeAttribute(attribute13, value) ++ encodeAttribute(attribute14, value) ++ encodeAttribute(attribute15, value) ++ encodeAttribute(attribute16, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5],
    attribute6: Attribute[T, A6],
    attribute7: Attribute[T, A7],
    attribute8: Attribute[T, A8],
    attribute9: Attribute[T, A9],
    attribute10: Attribute[T, A10],
    attribute11: Attribute[T, A11],
    attribute12: Attribute[T, A12],
    attribute13: Attribute[T, A13],
    attribute14: Attribute[T, A14],
    attribute15: Attribute[T, A15],
    attribute16: Attribute[T, A16],
    attribute17: Attribute[T, A17]
  )
  (constructor: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
          value6 <- decodeAttribute(attribute6, map)
          value7 <- decodeAttribute(attribute7, map)
          value8 <- decodeAttribute(attribute8, map)
          value9 <- decodeAttribute(attribute9, map)
          value10 <- decodeAttribute(attribute10, map)
          value11 <- decodeAttribute(attribute11, map)
          value12 <- decodeAttribute(attribute12, map)
          value13 <- decodeAttribute(attribute13, map)
          value14 <- decodeAttribute(attribute14, map)
          value15 <- decodeAttribute(attribute15, map)
          value16 <- decodeAttribute(attribute16, map)
          value17 <- decodeAttribute(attribute17, map)
        } yield constructor(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value) ++ encodeAttribute(attribute6, value) ++ encodeAttribute(attribute7, value) ++ encodeAttribute(attribute8, value) ++ encodeAttribute(attribute9, value) ++ encodeAttribute(attribute10, value) ++ encodeAttribute(attribute11, value) ++ encodeAttribute(attribute12, value) ++ encodeAttribute(attribute13, value) ++ encodeAttribute(attribute14, value) ++ encodeAttribute(attribute15, value) ++ encodeAttribute(attribute16, value) ++ encodeAttribute(attribute17, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5],
    attribute6: Attribute[T, A6],
    attribute7: Attribute[T, A7],
    attribute8: Attribute[T, A8],
    attribute9: Attribute[T, A9],
    attribute10: Attribute[T, A10],
    attribute11: Attribute[T, A11],
    attribute12: Attribute[T, A12],
    attribute13: Attribute[T, A13],
    attribute14: Attribute[T, A14],
    attribute15: Attribute[T, A15],
    attribute16: Attribute[T, A16],
    attribute17: Attribute[T, A17],
    attribute18: Attribute[T, A18]
  )
  (constructor: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
          value6 <- decodeAttribute(attribute6, map)
          value7 <- decodeAttribute(attribute7, map)
          value8 <- decodeAttribute(attribute8, map)
          value9 <- decodeAttribute(attribute9, map)
          value10 <- decodeAttribute(attribute10, map)
          value11 <- decodeAttribute(attribute11, map)
          value12 <- decodeAttribute(attribute12, map)
          value13 <- decodeAttribute(attribute13, map)
          value14 <- decodeAttribute(attribute14, map)
          value15 <- decodeAttribute(attribute15, map)
          value16 <- decodeAttribute(attribute16, map)
          value17 <- decodeAttribute(attribute17, map)
          value18 <- decodeAttribute(attribute18, map)
        } yield constructor(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value) ++ encodeAttribute(attribute6, value) ++ encodeAttribute(attribute7, value) ++ encodeAttribute(attribute8, value) ++ encodeAttribute(attribute9, value) ++ encodeAttribute(attribute10, value) ++ encodeAttribute(attribute11, value) ++ encodeAttribute(attribute12, value) ++ encodeAttribute(attribute13, value) ++ encodeAttribute(attribute14, value) ++ encodeAttribute(attribute15, value) ++ encodeAttribute(attribute16, value) ++ encodeAttribute(attribute17, value) ++ encodeAttribute(attribute18, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5],
    attribute6: Attribute[T, A6],
    attribute7: Attribute[T, A7],
    attribute8: Attribute[T, A8],
    attribute9: Attribute[T, A9],
    attribute10: Attribute[T, A10],
    attribute11: Attribute[T, A11],
    attribute12: Attribute[T, A12],
    attribute13: Attribute[T, A13],
    attribute14: Attribute[T, A14],
    attribute15: Attribute[T, A15],
    attribute16: Attribute[T, A16],
    attribute17: Attribute[T, A17],
    attribute18: Attribute[T, A18],
    attribute19: Attribute[T, A19]
  )
  (constructor: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
          value6 <- decodeAttribute(attribute6, map)
          value7 <- decodeAttribute(attribute7, map)
          value8 <- decodeAttribute(attribute8, map)
          value9 <- decodeAttribute(attribute9, map)
          value10 <- decodeAttribute(attribute10, map)
          value11 <- decodeAttribute(attribute11, map)
          value12 <- decodeAttribute(attribute12, map)
          value13 <- decodeAttribute(attribute13, map)
          value14 <- decodeAttribute(attribute14, map)
          value15 <- decodeAttribute(attribute15, map)
          value16 <- decodeAttribute(attribute16, map)
          value17 <- decodeAttribute(attribute17, map)
          value18 <- decodeAttribute(attribute18, map)
          value19 <- decodeAttribute(attribute19, map)
        } yield constructor(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value) ++ encodeAttribute(attribute6, value) ++ encodeAttribute(attribute7, value) ++ encodeAttribute(attribute8, value) ++ encodeAttribute(attribute9, value) ++ encodeAttribute(attribute10, value) ++ encodeAttribute(attribute11, value) ++ encodeAttribute(attribute12, value) ++ encodeAttribute(attribute13, value) ++ encodeAttribute(attribute14, value) ++ encodeAttribute(attribute15, value) ++ encodeAttribute(attribute16, value) ++ encodeAttribute(attribute17, value) ++ encodeAttribute(attribute18, value) ++ encodeAttribute(attribute19, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5],
    attribute6: Attribute[T, A6],
    attribute7: Attribute[T, A7],
    attribute8: Attribute[T, A8],
    attribute9: Attribute[T, A9],
    attribute10: Attribute[T, A10],
    attribute11: Attribute[T, A11],
    attribute12: Attribute[T, A12],
    attribute13: Attribute[T, A13],
    attribute14: Attribute[T, A14],
    attribute15: Attribute[T, A15],
    attribute16: Attribute[T, A16],
    attribute17: Attribute[T, A17],
    attribute18: Attribute[T, A18],
    attribute19: Attribute[T, A19],
    attribute20: Attribute[T, A20]
  )
  (constructor: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
          value6 <- decodeAttribute(attribute6, map)
          value7 <- decodeAttribute(attribute7, map)
          value8 <- decodeAttribute(attribute8, map)
          value9 <- decodeAttribute(attribute9, map)
          value10 <- decodeAttribute(attribute10, map)
          value11 <- decodeAttribute(attribute11, map)
          value12 <- decodeAttribute(attribute12, map)
          value13 <- decodeAttribute(attribute13, map)
          value14 <- decodeAttribute(attribute14, map)
          value15 <- decodeAttribute(attribute15, map)
          value16 <- decodeAttribute(attribute16, map)
          value17 <- decodeAttribute(attribute17, map)
          value18 <- decodeAttribute(attribute18, map)
          value19 <- decodeAttribute(attribute19, map)
          value20 <- decodeAttribute(attribute20, map)
        } yield constructor(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value) ++ encodeAttribute(attribute6, value) ++ encodeAttribute(attribute7, value) ++ encodeAttribute(attribute8, value) ++ encodeAttribute(attribute9, value) ++ encodeAttribute(attribute10, value) ++ encodeAttribute(attribute11, value) ++ encodeAttribute(attribute12, value) ++ encodeAttribute(attribute13, value) ++ encodeAttribute(attribute14, value) ++ encodeAttribute(attribute15, value) ++ encodeAttribute(attribute16, value) ++ encodeAttribute(attribute17, value) ++ encodeAttribute(attribute18, value) ++ encodeAttribute(attribute19, value) ++ encodeAttribute(attribute20, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5],
    attribute6: Attribute[T, A6],
    attribute7: Attribute[T, A7],
    attribute8: Attribute[T, A8],
    attribute9: Attribute[T, A9],
    attribute10: Attribute[T, A10],
    attribute11: Attribute[T, A11],
    attribute12: Attribute[T, A12],
    attribute13: Attribute[T, A13],
    attribute14: Attribute[T, A14],
    attribute15: Attribute[T, A15],
    attribute16: Attribute[T, A16],
    attribute17: Attribute[T, A17],
    attribute18: Attribute[T, A18],
    attribute19: Attribute[T, A19],
    attribute20: Attribute[T, A20],
    attribute21: Attribute[T, A21]
  )
  (constructor: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
          value6 <- decodeAttribute(attribute6, map)
          value7 <- decodeAttribute(attribute7, map)
          value8 <- decodeAttribute(attribute8, map)
          value9 <- decodeAttribute(attribute9, map)
          value10 <- decodeAttribute(attribute10, map)
          value11 <- decodeAttribute(attribute11, map)
          value12 <- decodeAttribute(attribute12, map)
          value13 <- decodeAttribute(attribute13, map)
          value14 <- decodeAttribute(attribute14, map)
          value15 <- decodeAttribute(attribute15, map)
          value16 <- decodeAttribute(attribute16, map)
          value17 <- decodeAttribute(attribute17, map)
          value18 <- decodeAttribute(attribute18, map)
          value19 <- decodeAttribute(attribute19, map)
          value20 <- decodeAttribute(attribute20, map)
          value21 <- decodeAttribute(attribute21, map)
        } yield constructor(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value) ++ encodeAttribute(attribute6, value) ++ encodeAttribute(attribute7, value) ++ encodeAttribute(attribute8, value) ++ encodeAttribute(attribute9, value) ++ encodeAttribute(attribute10, value) ++ encodeAttribute(attribute11, value) ++ encodeAttribute(attribute12, value) ++ encodeAttribute(attribute13, value) ++ encodeAttribute(attribute14, value) ++ encodeAttribute(attribute15, value) ++ encodeAttribute(attribute16, value) ++ encodeAttribute(attribute17, value) ++ encodeAttribute(attribute18, value) ++ encodeAttribute(attribute19, value) ++ encodeAttribute(attribute20, value) ++ encodeAttribute(attribute21, value)
      }
    }
  }


  def apply[A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22]
  (
    attribute1: Attribute[T, A1],
    attribute2: Attribute[T, A2],
    attribute3: Attribute[T, A3],
    attribute4: Attribute[T, A4],
    attribute5: Attribute[T, A5],
    attribute6: Attribute[T, A6],
    attribute7: Attribute[T, A7],
    attribute8: Attribute[T, A8],
    attribute9: Attribute[T, A9],
    attribute10: Attribute[T, A10],
    attribute11: Attribute[T, A11],
    attribute12: Attribute[T, A12],
    attribute13: Attribute[T, A13],
    attribute14: Attribute[T, A14],
    attribute15: Attribute[T, A15],
    attribute16: Attribute[T, A16],
    attribute17: Attribute[T, A17],
    attribute18: Attribute[T, A18],
    attribute19: Attribute[T, A19],
    attribute20: Attribute[T, A20],
    attribute21: Attribute[T, A21],
    attribute22: Attribute[T, A22]
  )
  (constructor: (A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22) => T): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        for {
          value1 <- decodeAttribute(attribute1, map)
          value2 <- decodeAttribute(attribute2, map)
          value3 <- decodeAttribute(attribute3, map)
          value4 <- decodeAttribute(attribute4, map)
          value5 <- decodeAttribute(attribute5, map)
          value6 <- decodeAttribute(attribute6, map)
          value7 <- decodeAttribute(attribute7, map)
          value8 <- decodeAttribute(attribute8, map)
          value9 <- decodeAttribute(attribute9, map)
          value10 <- decodeAttribute(attribute10, map)
          value11 <- decodeAttribute(attribute11, map)
          value12 <- decodeAttribute(attribute12, map)
          value13 <- decodeAttribute(attribute13, map)
          value14 <- decodeAttribute(attribute14, map)
          value15 <- decodeAttribute(attribute15, map)
          value16 <- decodeAttribute(attribute16, map)
          value17 <- decodeAttribute(attribute17, map)
          value18 <- decodeAttribute(attribute18, map)
          value19 <- decodeAttribute(attribute19, map)
          value20 <- decodeAttribute(attribute20, map)
          value21 <- decodeAttribute(attribute21, map)
          value22 <- decodeAttribute(attribute22, map)
        } yield constructor(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22)
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        encodeAttribute(attribute1, value) ++ encodeAttribute(attribute2, value) ++ encodeAttribute(attribute3, value) ++ encodeAttribute(attribute4, value) ++ encodeAttribute(attribute5, value) ++ encodeAttribute(attribute6, value) ++ encodeAttribute(attribute7, value) ++ encodeAttribute(attribute8, value) ++ encodeAttribute(attribute9, value) ++ encodeAttribute(attribute10, value) ++ encodeAttribute(attribute11, value) ++ encodeAttribute(attribute12, value) ++ encodeAttribute(attribute13, value) ++ encodeAttribute(attribute14, value) ++ encodeAttribute(attribute15, value) ++ encodeAttribute(attribute16, value) ++ encodeAttribute(attribute17, value) ++ encodeAttribute(attribute18, value) ++ encodeAttribute(attribute19, value) ++ encodeAttribute(attribute20, value) ++ encodeAttribute(attribute21, value) ++ encodeAttribute(attribute22, value)
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

class CompositeConverterWithOptionsBuilder[T, A](attributeName: String, defaultValue: A)
                                                (implicit attributeConverter: SimpleConverter[A]) {

  def options[T1 <: T : ClassTag, T2 <: T : ClassTag]
  (
    option1: (A, CompositeConverter[T1]),
    option2: (A, CompositeConverter[T2])
  ): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        discriminatorAttributeValue(map)
          .flatMap {
            case option1._1 => option1._2.decode(map)
            case option2._1 => option2._2.decode(map)
            case another => Left(DecodingError(s"The attribute '$attributeName' has an unknown value '$another'"))
          }
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        value match {
          case v: T1 => option1._2.encode(v) + (attributeName -> attributeConverter.encode(option1._1))
          case v: T2 => option2._2.encode(v) + (attributeName -> attributeConverter.encode(option2._1))
          case v =>
            throw new IllegalArgumentException(
              s"The class '${v.getClass.getName}' cannot be encoded. Probably, you forgot to put its converter as a variant."
            )
        }
      }
    }
  }

  def options[T1 <: T : ClassTag, T2 <: T : ClassTag, T3 <: T : ClassTag]
  (
    option1: (A, CompositeConverter[T1]),
    option2: (A, CompositeConverter[T2]),
    option3: (A, CompositeConverter[T3])
  ): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        discriminatorAttributeValue(map)
          .flatMap {
            case option1._1 => option1._2.decode(map)
            case option2._1 => option2._2.decode(map)
            case option3._1 => option3._2.decode(map)
            case another => Left(DecodingError(s"The attribute '$attributeName' has an unknown value '$another'"))
          }
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        value match {
          case v: T1 => option1._2.encode(v) + (attributeName -> attributeConverter.encode(option1._1))
          case v: T2 => option2._2.encode(v) + (attributeName -> attributeConverter.encode(option2._1))
          case v: T3 => option3._2.encode(v) + (attributeName -> attributeConverter.encode(option3._1))
          case v =>
            throw new IllegalArgumentException(
              s"The class '${v.getClass.getName}' cannot be encoded. Probably, you forgot to put its converter as a variant."
            )
        }
      }
    }
  }

  def options[T1 <: T : ClassTag, T2 <: T : ClassTag, T3 <: T : ClassTag, T4 <: T : ClassTag]
  (
    option1: (A, CompositeConverter[T1]),
    option2: (A, CompositeConverter[T2]),
    option3: (A, CompositeConverter[T3]),
    option4: (A, CompositeConverter[T4])
  ): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        discriminatorAttributeValue(map)
          .flatMap {
            case option1._1 => option1._2.decode(map)
            case option2._1 => option2._2.decode(map)
            case option3._1 => option3._2.decode(map)
            case option4._1 => option4._2.decode(map)
            case another => Left(DecodingError(s"The attribute '$attributeName' has an unknown value '$another'"))
          }
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        value match {
          case v: T1 => option1._2.encode(v) + (attributeName -> attributeConverter.encode(option1._1))
          case v: T2 => option2._2.encode(v) + (attributeName -> attributeConverter.encode(option2._1))
          case v: T3 => option3._2.encode(v) + (attributeName -> attributeConverter.encode(option3._1))
          case v: T4 => option4._2.encode(v) + (attributeName -> attributeConverter.encode(option4._1))
          case v =>
            throw new IllegalArgumentException(
              s"The class '${v.getClass.getName}' cannot be encoded. Probably, you forgot to put its converter as a variant."
            )
        }
      }
    }
  }

  def options[T1 <: T : ClassTag, T2 <: T : ClassTag, T3 <: T : ClassTag, T4 <: T : ClassTag, T5 <: T : ClassTag]
  (
    option1: (A, CompositeConverter[T1]),
    option2: (A, CompositeConverter[T2]),
    option3: (A, CompositeConverter[T3]),
    option4: (A, CompositeConverter[T4]),
    option5: (A, CompositeConverter[T5])
  ): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        discriminatorAttributeValue(map)
          .flatMap {
            case option1._1 => option1._2.decode(map)
            case option2._1 => option2._2.decode(map)
            case option3._1 => option3._2.decode(map)
            case option4._1 => option4._2.decode(map)
            case option5._1 => option5._2.decode(map)
            case another => Left(DecodingError(s"The attribute '$attributeName' has an unknown value '$another'"))
          }
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        value match {
          case v: T1 => option1._2.encode(v) + (attributeName -> attributeConverter.encode(option1._1))
          case v: T2 => option2._2.encode(v) + (attributeName -> attributeConverter.encode(option2._1))
          case v: T3 => option3._2.encode(v) + (attributeName -> attributeConverter.encode(option3._1))
          case v: T4 => option4._2.encode(v) + (attributeName -> attributeConverter.encode(option4._1))
          case v: T5 => option5._2.encode(v) + (attributeName -> attributeConverter.encode(option5._1))
          case v =>
            throw new IllegalArgumentException(
              s"The class '${v.getClass.getName}' cannot be encoded. Probably, you forgot to put its converter as a variant."
            )
        }
      }
    }
  }

  def options[T1 <: T : ClassTag, T2 <: T : ClassTag, T3 <: T : ClassTag, T4 <: T : ClassTag, T5 <: T : ClassTag, T6 <: T : ClassTag]
  (
    option1: (A, CompositeConverter[T1]),
    option2: (A, CompositeConverter[T2]),
    option3: (A, CompositeConverter[T3]),
    option4: (A, CompositeConverter[T4]),
    option5: (A, CompositeConverter[T5]),
    option6: (A, CompositeConverter[T6])
  ): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        discriminatorAttributeValue(map)
          .flatMap {
            case option1._1 => option1._2.decode(map)
            case option2._1 => option2._2.decode(map)
            case option3._1 => option3._2.decode(map)
            case option4._1 => option4._2.decode(map)
            case option5._1 => option5._2.decode(map)
            case option6._1 => option6._2.decode(map)
            case another => Left(DecodingError(s"The attribute '$attributeName' has an unknown value '$another'"))
          }
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        value match {
          case v: T1 => option1._2.encode(v) + (attributeName -> attributeConverter.encode(option1._1))
          case v: T2 => option2._2.encode(v) + (attributeName -> attributeConverter.encode(option2._1))
          case v: T3 => option3._2.encode(v) + (attributeName -> attributeConverter.encode(option3._1))
          case v: T4 => option4._2.encode(v) + (attributeName -> attributeConverter.encode(option4._1))
          case v: T5 => option5._2.encode(v) + (attributeName -> attributeConverter.encode(option5._1))
          case v: T6 => option6._2.encode(v) + (attributeName -> attributeConverter.encode(option6._1))
          case v =>
            throw new IllegalArgumentException(
              s"The class '${v.getClass.getName}' cannot be encoded. Probably, you forgot to put its converter as a variant."
            )
        }
      }
    }
  }

  def options[T1 <: T : ClassTag, T2 <: T : ClassTag, T3 <: T : ClassTag, T4 <: T : ClassTag, T5 <: T : ClassTag, T6 <: T : ClassTag, T7 <: T : ClassTag]
  (
    option1: (A, CompositeConverter[T1]),
    option2: (A, CompositeConverter[T2]),
    option3: (A, CompositeConverter[T3]),
    option4: (A, CompositeConverter[T4]),
    option5: (A, CompositeConverter[T5]),
    option6: (A, CompositeConverter[T6]),
    option7: (A, CompositeConverter[T7])
  ): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        discriminatorAttributeValue(map)
          .flatMap {
            case option1._1 => option1._2.decode(map)
            case option2._1 => option2._2.decode(map)
            case option3._1 => option3._2.decode(map)
            case option4._1 => option4._2.decode(map)
            case option5._1 => option5._2.decode(map)
            case option6._1 => option6._2.decode(map)
            case option7._1 => option7._2.decode(map)
            case another => Left(DecodingError(s"The attribute '$attributeName' has an unknown value '$another'"))
          }
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        value match {
          case v: T1 => option1._2.encode(v) + (attributeName -> attributeConverter.encode(option1._1))
          case v: T2 => option2._2.encode(v) + (attributeName -> attributeConverter.encode(option2._1))
          case v: T3 => option3._2.encode(v) + (attributeName -> attributeConverter.encode(option3._1))
          case v: T4 => option4._2.encode(v) + (attributeName -> attributeConverter.encode(option4._1))
          case v: T5 => option5._2.encode(v) + (attributeName -> attributeConverter.encode(option5._1))
          case v: T6 => option6._2.encode(v) + (attributeName -> attributeConverter.encode(option6._1))
          case v: T7 => option7._2.encode(v) + (attributeName -> attributeConverter.encode(option7._1))
          case v =>
            throw new IllegalArgumentException(
              s"The class '${v.getClass.getName}' cannot be encoded. Probably, you forgot to put its converter as a variant."
            )
        }
      }
    }
  }

  def options[T1 <: T : ClassTag, T2 <: T : ClassTag, T3 <: T : ClassTag, T4 <: T : ClassTag, T5 <: T : ClassTag, T6 <: T : ClassTag, T7 <: T : ClassTag, T8 <: T : ClassTag]
  (
    option1: (A, CompositeConverter[T1]),
    option2: (A, CompositeConverter[T2]),
    option3: (A, CompositeConverter[T3]),
    option4: (A, CompositeConverter[T4]),
    option5: (A, CompositeConverter[T5]),
    option6: (A, CompositeConverter[T6]),
    option7: (A, CompositeConverter[T7]),
    option8: (A, CompositeConverter[T8])
  ): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        discriminatorAttributeValue(map)
          .flatMap {
            case option1._1 => option1._2.decode(map)
            case option2._1 => option2._2.decode(map)
            case option3._1 => option3._2.decode(map)
            case option4._1 => option4._2.decode(map)
            case option5._1 => option5._2.decode(map)
            case option6._1 => option6._2.decode(map)
            case option7._1 => option7._2.decode(map)
            case option8._1 => option8._2.decode(map)
            case another => Left(DecodingError(s"The attribute '$attributeName' has an unknown value '$another'"))
          }
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        value match {
          case v: T1 => option1._2.encode(v) + (attributeName -> attributeConverter.encode(option1._1))
          case v: T2 => option2._2.encode(v) + (attributeName -> attributeConverter.encode(option2._1))
          case v: T3 => option3._2.encode(v) + (attributeName -> attributeConverter.encode(option3._1))
          case v: T4 => option4._2.encode(v) + (attributeName -> attributeConverter.encode(option4._1))
          case v: T5 => option5._2.encode(v) + (attributeName -> attributeConverter.encode(option5._1))
          case v: T6 => option6._2.encode(v) + (attributeName -> attributeConverter.encode(option6._1))
          case v: T7 => option7._2.encode(v) + (attributeName -> attributeConverter.encode(option7._1))
          case v: T8 => option8._2.encode(v) + (attributeName -> attributeConverter.encode(option8._1))
          case v =>
            throw new IllegalArgumentException(
              s"The class '${v.getClass.getName}' cannot be encoded. Probably, you forgot to put its converter as a variant."
            )
        }
      }
    }
  }

  def options[T1 <: T : ClassTag, T2 <: T : ClassTag, T3 <: T : ClassTag, T4 <: T : ClassTag, T5 <: T : ClassTag, T6 <: T : ClassTag, T7 <: T : ClassTag, T8 <: T : ClassTag, T9 <: T : ClassTag]
  (
    option1: (A, CompositeConverter[T1]),
    option2: (A, CompositeConverter[T2]),
    option3: (A, CompositeConverter[T3]),
    option4: (A, CompositeConverter[T4]),
    option5: (A, CompositeConverter[T5]),
    option6: (A, CompositeConverter[T6]),
    option7: (A, CompositeConverter[T7]),
    option8: (A, CompositeConverter[T8]),
    option9: (A, CompositeConverter[T9])
  ): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        discriminatorAttributeValue(map)
          .flatMap {
            case option1._1 => option1._2.decode(map)
            case option2._1 => option2._2.decode(map)
            case option3._1 => option3._2.decode(map)
            case option4._1 => option4._2.decode(map)
            case option5._1 => option5._2.decode(map)
            case option6._1 => option6._2.decode(map)
            case option7._1 => option7._2.decode(map)
            case option8._1 => option8._2.decode(map)
            case option9._1 => option9._2.decode(map)
            case another => Left(DecodingError(s"The attribute '$attributeName' has an unknown value '$another'"))
          }
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        value match {
          case v: T1 => option1._2.encode(v) + (attributeName -> attributeConverter.encode(option1._1))
          case v: T2 => option2._2.encode(v) + (attributeName -> attributeConverter.encode(option2._1))
          case v: T3 => option3._2.encode(v) + (attributeName -> attributeConverter.encode(option3._1))
          case v: T4 => option4._2.encode(v) + (attributeName -> attributeConverter.encode(option4._1))
          case v: T5 => option5._2.encode(v) + (attributeName -> attributeConverter.encode(option5._1))
          case v: T6 => option6._2.encode(v) + (attributeName -> attributeConverter.encode(option6._1))
          case v: T7 => option7._2.encode(v) + (attributeName -> attributeConverter.encode(option7._1))
          case v: T8 => option8._2.encode(v) + (attributeName -> attributeConverter.encode(option8._1))
          case v: T9 => option9._2.encode(v) + (attributeName -> attributeConverter.encode(option9._1))
          case v =>
            throw new IllegalArgumentException(
              s"The class '${v.getClass.getName}' cannot be encoded. Probably, you forgot to put its converter as a variant."
            )
        }
      }
    }
  }

  def options[T1 <: T : ClassTag, T2 <: T : ClassTag, T3 <: T : ClassTag, T4 <: T : ClassTag, T5 <: T : ClassTag, T6 <: T : ClassTag, T7 <: T : ClassTag, T8 <: T : ClassTag, T9 <: T : ClassTag, T10 <: T : ClassTag]
  (
    option1: (A, CompositeConverter[T1]),
    option2: (A, CompositeConverter[T2]),
    option3: (A, CompositeConverter[T3]),
    option4: (A, CompositeConverter[T4]),
    option5: (A, CompositeConverter[T5]),
    option6: (A, CompositeConverter[T6]),
    option7: (A, CompositeConverter[T7]),
    option8: (A, CompositeConverter[T8]),
    option9: (A, CompositeConverter[T9]),
    option10: (A, CompositeConverter[T10])
  ): CompositeConverter[T] = {
    new CompositeConverter[T] {
      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
        discriminatorAttributeValue(map)
          .flatMap {
            case option1._1 => option1._2.decode(map)
            case option2._1 => option2._2.decode(map)
            case option3._1 => option3._2.decode(map)
            case option4._1 => option4._2.decode(map)
            case option5._1 => option5._2.decode(map)
            case option6._1 => option6._2.decode(map)
            case option7._1 => option7._2.decode(map)
            case option8._1 => option8._2.decode(map)
            case option9._1 => option9._2.decode(map)
            case option10._1 => option10._2.decode(map)
            case another => Left(DecodingError(s"The attribute '$attributeName' has an unknown value '$another'"))
          }
      }

      override def encode(value: T): Map[String, AttributeValue] = {
        value match {
          case v: T1 => option1._2.encode(v) + (attributeName -> attributeConverter.encode(option1._1))
          case v: T2 => option2._2.encode(v) + (attributeName -> attributeConverter.encode(option2._1))
          case v: T3 => option3._2.encode(v) + (attributeName -> attributeConverter.encode(option3._1))
          case v: T4 => option4._2.encode(v) + (attributeName -> attributeConverter.encode(option4._1))
          case v: T5 => option5._2.encode(v) + (attributeName -> attributeConverter.encode(option5._1))
          case v: T6 => option6._2.encode(v) + (attributeName -> attributeConverter.encode(option6._1))
          case v: T7 => option7._2.encode(v) + (attributeName -> attributeConverter.encode(option7._1))
          case v: T8 => option8._2.encode(v) + (attributeName -> attributeConverter.encode(option8._1))
          case v: T9 => option9._2.encode(v) + (attributeName -> attributeConverter.encode(option9._1))
          case v: T10 => option10._2.encode(v) + (attributeName -> attributeConverter.encode(option10._1))
          case v =>
            throw new IllegalArgumentException(
              s"The class '${v.getClass.getName}' cannot be encoded. Probably, you forgot to put its converter as a variant."
            )
        }
      }
    }
  }

  private def discriminatorAttributeValue(map: Map[String, AttributeValue]) = {
    map.get(attributeName)
      .map(implicitly[SimpleConverter[A]].decode)
      .map(_.left.map(DecodingError(s"The attribute '$attributeName' cannot be decoded", _)))
      .getOrElse(Right(defaultValue))
  }

}
