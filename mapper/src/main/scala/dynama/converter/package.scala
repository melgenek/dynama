package dynama

import software.amazon.awssdk.services.dynamodb.model.AttributeValue

package object converter {

  final case class DecodingError(message: String, cause: Throwable = null) extends RuntimeException(message, cause) {
    override def toString: String = {
      if (cause != null) s"$message. ${cause.getMessage}"
      else message
    }
  }

  type DecodingResult[T] = Either[DecodingError, T]

  final val NulAttributeValue = AttributeValue.builder().nul(true).build()

}
