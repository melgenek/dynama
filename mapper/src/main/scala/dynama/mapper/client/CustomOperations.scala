package dynama.mapper.client

import java.util.concurrent.{CompletableFuture, CompletionException, CompletionStage, Executors, TimeUnit}
import java.util.function

import software.amazon.awssdk.services.dynamodb.model.{CreateTableRequest, CreateTableResponse, DeleteTableRequest, DeleteTableResponse, DescribeTableRequest, DescribeTableResponse, ResourceNotFoundException, TableDescription, TableStatus}

import scala.language.higherKinds

trait CustomOperations[F[_]] extends AutoCloseable {
  this: DynamoClient[F, _] =>

  private val scheduler = Executors.newSingleThreadScheduledExecutor()

  def createTableAndWaitForActiveStatus(createTableRequest: CreateTableRequest,
                                        describeTableRequest: DescribeTableRequest,
                                        retryIntervalMillis: Long = 100): F[Unit] = {
    val waitForCreationFunction: function.Function[CreateTableResponse, CompletionStage[Unit]] =
      _ => waitForActiveTableStatus(describeTableRequest, retryIntervalMillis)
    completableFutureConverter.fromCompletableFuture(
      underlying.createTable(createTableRequest).thenComposeAsync(waitForCreationFunction, scheduler)
    )
  }

  private def waitForActiveTableStatus(describeTableRequest: DescribeTableRequest,
                                       retryInterval: Long): CompletableFuture[Unit] = {
    val retryFunction: function.Function[_ >: DescribeTableResponse, _ <: CompletionStage[Unit]] = table =>
      if (table.table().tableStatus() == TableStatus.ACTIVE) CompletableFuture.completedFuture(())
      else waitForActiveTableStatus(describeTableRequest, retryInterval)
    scheduleAsync(
      underlying.describeTable(describeTableRequest).exceptionally {
        case _: ResourceNotFoundException =>
          DescribeTableResponse.builder()
            .table(tableBuilder => tableBuilder.tableStatus(TableStatus.CREATING))
            .build()
      },
      retryInterval
    ).thenComposeAsync(retryFunction, scheduler)
  }

  def deleteTableAndWaitForDeletedStatus(deleteTableRequest: DeleteTableRequest,
                                         describeTableRequest: DescribeTableRequest,
                                         retryInterval: Long = 100): F[Unit] = {
    val waitForDeletionFunction: function.Function[DeleteTableResponse, CompletionStage[Unit]] =
      _ => waitForDeletedTableStatus(describeTableRequest, retryInterval)
    completableFutureConverter.fromCompletableFuture(
      underlying.deleteTable(deleteTableRequest).thenComposeAsync(waitForDeletionFunction, scheduler)
    )
  }

  private def waitForDeletedTableStatus(describeTableRequest: DescribeTableRequest,
                                        retryInterval: Long): CompletableFuture[Unit] = {
    val retryFunction: function.Function[Boolean, CompletionStage[Unit]] = isDeleted =>
      if (isDeleted) CompletableFuture.completedFuture(())
      else waitForDeletedTableStatus(describeTableRequest, retryInterval)
    val responseToBoolean: function.Function[DescribeTableResponse, Boolean] = _ => false
    scheduleAsync(
      underlying.describeTable(describeTableRequest)
        .thenApplyAsync[Boolean](responseToBoolean, scheduler)
        .exceptionally {
          case e: CompletionException if e.getCause.isInstanceOf[ResourceNotFoundException] =>
            true
          case e =>
            throw e
        },
      retryInterval
    ).thenComposeAsync(retryFunction, scheduler)
  }

  private def scheduleAsync[T](task: => CompletableFuture[T], delay: Long): CompletableFuture[T] = {
    val completableFuture = new CompletableFuture[T]
    scheduler.schedule(() => {
      task
        .thenAccept(res => completableFuture.complete(res))
        .exceptionally { e =>
          completableFuture.completeExceptionally(e)
          null
        }
    }, delay, TimeUnit.MILLISECONDS)
    completableFuture
  }

  override def close(): Unit = {
    scheduler.shutdown()
    ()
  }

}
