package dynama.converter

object Generate extends App {

  for (i <- 1 to 22) {
    val range = 1 to i


    val converter =
      s""""Class with $i attributes" should behave like conversionCheck(A$i(${range.map(x => s""""a$x"""").mkString(",")}), A${i}Converter)""".stripMargin

    println(converter)

  }


}
