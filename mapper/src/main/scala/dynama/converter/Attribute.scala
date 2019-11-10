package dynama.converter

sealed trait Attribute[T, A]

object Attribute {

  private[converter] final case class Simple[T, A](name: String, get: T => A)(implicit val converter: SimpleConverter[A]) extends Attribute[T, A]

  private[converter] final case class Composite[T, A](get: T => A)(implicit val converter: CompositeConverter[A]) extends Attribute[T, A]

  def apply[T, A](name: String, get: T => A)(implicit attributeConverter: SimpleConverter[A]): Attribute[T, A] = Simple(name, get)

  def flattened[T, A](get: T => A)(implicit itemConverter: CompositeConverter[A]): Attribute[T, A] = Composite(get)

}
