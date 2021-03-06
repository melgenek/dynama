package dynama.client

import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient

import scala.language.higherKinds

class DynamoClient[F[_]](val underlying: DynamoDbAsyncClient)(implicit val F: FLifter[F])
  extends GetOperations[F]
    with PutOperations[F] {

}
