package dynama.table

import dynama.converter.{Attribute, SimpleConverter}

final case class PartitionKey[A](name: String)(implicit val converter: SimpleConverter[A]) {
  def toAttribute[T](get: T => A): Attribute[T, A] = Attribute(name, get)
}


