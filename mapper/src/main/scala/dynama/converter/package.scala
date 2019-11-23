package dynama

package object converter {

  final case class DecodingError(message: String, cause: Throwable = null) extends RuntimeException(message, cause)

  type DecodingResult[T] = Either[DecodingError, T]

}
