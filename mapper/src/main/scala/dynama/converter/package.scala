package dynama

package object converter {

  final case class DecodingError(message: String, cause: Throwable = null) extends RuntimeException(message, cause)

  final case class EncodingError(message: String, cause: Throwable = null) extends RuntimeException(message, cause)

  type DecodingResult[T] = Either[DecodingError, T]

  type EncodingResult[T] = Either[EncodingError, T]

}
