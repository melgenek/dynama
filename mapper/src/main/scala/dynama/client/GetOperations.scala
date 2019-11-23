package dynama.client

import java.util.concurrent.CompletableFuture

import dynama.request.DynamoRequestWithResponse
import dynama.util.CompletableFutureUtil
import software.amazon.awssdk.services.dynamodb.model.{GetItemRequest, GetItemResponse}

import scala.collection.JavaConverters._
import scala.language.higherKinds

trait GetOperations[F[_]] {
  _: DynamoClient[F] =>

  def run(request: GetItemRequest.Builder): F[GetItemResponse] = {
    F.liftF {
      underlying.getItem(request.build())
    }
  }

  def run[T](request: DynamoRequestWithResponse[GetItemRequest.Builder, T]): F[T] = {
    F.liftF {
      underlying
        .getItem(request.builder.build())
        .thenCompose[T] { response =>
          request.itemConverter.decode(response.item().asScala.toMap)
            .map(item => CompletableFuture.completedFuture[T](item))
            .left.map(e => CompletableFutureUtil.failedFuture[T](e))
            .merge
        }
    }
  }

}
