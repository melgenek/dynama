package dynama.converter

object Generate extends App {

  for (i <- 2 to 10) {
    val range = 1 to i


    val converter =
      s"""def options[${range.map(x => s"T$x <: T : ClassTag").mkString(",")}]
         |                       (
         |                       ${range.map(x => s"option$x: (A, CompositeConverter[T$x])").mkString(",\n")}
         |                        ): CompositeConverter[T] = {
         |    new CompositeConverter[T] {
         |      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
         |discriminatorAttributeValue(map)
         |          .flatMap {
         |             ${range.map(x => s"case option$x._1 => option$x._2.decode(map)").mkString("\n")}
         |            case another => Left(DecodingError(s"The attribute '$$attributeName' has an unknown value '$$another'"))
         |          }
         |      }
         |
         |      override def encode(value: T): Map[String, AttributeValue] = {
         |        value match {
         |                     ${range.map(x => s"case v: T$x => option$x._2.encode(v)  + (attributeName -> attributeConverter.encode(option$x._1))").mkString("\n")}
         |                               case v =>
         |            throw new IllegalArgumentException(
         |              s"The class '$${v.getClass.getName}' cannot be encoded. Probably, you forgot to put its converter as a variant."
         |            )
         |        }
         |      }
         |    }
         |  }
         |""".stripMargin

    println(converter)

  }


}
