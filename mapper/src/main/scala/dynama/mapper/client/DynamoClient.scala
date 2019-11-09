package dynama.mapper.client

import dynama.mapper.{CompletableFutureConverter, ItemReader}
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.{CreateTableRequest, DeleteTableRequest, DescribeTableRequest, GetItemRequest, PutItemRequest, TableDescription}

import scala.collection.JavaConverters._
import scala.language.higherKinds

class DynamoClient[F[_] : CompletableFutureConverter, T: ItemReader](val underlying: DynamoDbAsyncClient) {

  protected val completableFutureConverter = implicitly[CompletableFutureConverter[F]]
  protected val itemReader = implicitly[ItemReader[T]]

  def createTable(request: CreateTableRequest): F[Unit] = {
    completableFutureConverter.fromCompletableFuture {
      underlying.createTable(request).thenApplyAsync(_ => ())
    }
  }

  def deleteTable(request: DeleteTableRequest): F[Unit] = {
    completableFutureConverter.fromCompletableFuture {
      underlying.deleteTable(request).thenApplyAsync(_ => ())
    }
  }

  def describeTable(request: DescribeTableRequest): F[TableDescription] = {
    completableFutureConverter.fromCompletableFuture {
      underlying.describeTable(request).thenApplyAsync(response => response.table())
    }
  }

  def putItem(request: PutItemRequest): F[Unit] = {
    completableFutureConverter.fromCompletableFuture {
      underlying.putItem(request).thenApplyAsync(_ => ())
    }
  }

  def getItem(request: GetItemRequest): F[T] = {
    completableFutureConverter.fromCompletableFuture {
      underlying.getItem(request).thenApplyAsync(response => itemReader.fromMap(response.item().asScala.toMap))
    }
  }

}
