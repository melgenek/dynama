package dynama.converter

private[converter] object GenerateCompositeConverter extends App {

  for (i <- 1 to 22) {
    val range = 1 to i


    val converter =
      s"""def apply[${range.map(x => s"A$x").mkString(",")}]
         |                       (
         |                       ${range.map(x => s"attribute$x: Attribute[T, A$x]").mkString(",\n")}
         |                       )
         |                       (constructor: (${range.map(x => s"A$x").mkString(",")}) => T): CompositeConverter[T] = {
         |    new CompositeConverter[T] {
         |      override def decode(map: Map[String, AttributeValue]): DecodingResult[T] = {
         |        for {
         |           ${range.map(x => s"value$x <- attribute$x.converter.decode(map)").mkString("\n")}
         |        } yield constructor(${range.map(x => s"value$x").mkString(",")})
         |      }
         |
         |      override def encode(value: T): Map[String, AttributeValue] = {
         |        ${range.map(x => s"attribute$x.converter.encode(attribute$x.get(value))").mkString(" ++\n")}
         |      }
         |    }
         |  }
         |""".stripMargin

    println(converter)

  }


}
