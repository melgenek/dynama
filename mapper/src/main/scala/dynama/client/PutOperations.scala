package dynama.client

import java.util.concurrent.CompletableFuture

import dynama.request.{DynamoRequest, DynamoRequestWithResponse}
import dynama.util.CompletableFutureUtil
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest

import scala.collection.JavaConverters._
import scala.language.higherKinds

trait PutOperations[F[_]] {
  _: DynamoClient[F] =>

  def run(request: DynamoRequest[PutItemRequest.Builder]): F[Unit] = {
    request.builder
      .map { builder =>
        F.liftF {
          underlying
            .putItem(builder.build())
            .thenCompose[Unit] { _ => CompletableFuture.completedFuture[Unit](()) }
        }
      }
      .left.map(F.raiseError[Unit])
      .merge
  }

  def run[T](request: DynamoRequestWithResponse[PutItemRequest.Builder, T]): F[T] = {
    request.builder
      .map { builder =>
        F.liftF {
          underlying
            .putItem(builder.build())
            .thenCompose[T] { response =>
              request.itemConverter.decode(response.attributes().asScala.toMap)
                .map(item => CompletableFuture.completedFuture[T](item))
                .left.map(e => CompletableFutureUtil.failedFuture[T](e))
                .merge
            }
        }
      }
      .left.map(F.raiseError[T])
      .merge
  }

}
