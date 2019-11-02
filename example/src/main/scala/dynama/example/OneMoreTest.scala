package dynama.example

import dynama.mapper.ItemConverter

object OneMoreTest extends App {

  val entityConverter = ItemConverter.converter[MyEntity]


  println(MyEntity("1", 1))
  //  val g = MyEntity.Name

}


case class MyEntity(field1: String, field2: Int)

