package dynama.converter

private[dynama] sealed trait Attribute[T, A]

object Attribute {

  private[converter] final case class Simple[T, A](name: String, get: T => A)(implicit val converter: SimpleConverter[A]) extends Attribute[T, A]

  private[converter] final case class Flat[T, A](get: T => A)(implicit val converter: CompositeConverter[A]) extends Attribute[T, A]

  def apply[T, A: SimpleConverter](name: String, get: T => A): Simple[T, A] = Simple(name, get)

  def flat[T, A: CompositeConverter](get: T => A): Flat[T, A] = Flat(get)

}
