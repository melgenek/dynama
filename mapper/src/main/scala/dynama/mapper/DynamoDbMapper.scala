package dynama.mapper

import scala.language.higherKinds

trait DynamoDbMapper[F[_]] {

  def getItem[A: ItemConverter, K: AttributeConverter, S: AttributeConverter](partitionKey: K, sortKey: Option[S] = None): F[A]

}
