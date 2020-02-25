package dynama.converter

private[dynama] sealed trait Attribute[T, A] {
  val get: T => A
  val converter: CompositeConverter[A]
}

object Attribute {
  def apply[T, A](name: String, getFromCaseClass: T => A)
                 (implicit simpleConverter: SimpleConverter[A]): Attribute[T, A] = new Attribute[T, A] {
    override val get: T => A = getFromCaseClass
    override val converter: CompositeConverter[A] = simpleConverter(name)
  }

  def flat[T, A](getFromCaseClass: T => A)
                (implicit compositeConverter: CompositeConverter[A]): Attribute[T, A] = new Attribute[T, A] {
    override val get: T => A = getFromCaseClass
    override val converter: CompositeConverter[A] = compositeConverter
  }
}
