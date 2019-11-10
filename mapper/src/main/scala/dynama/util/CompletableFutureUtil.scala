package dynama.util

import java.util.concurrent.CompletableFuture

object CompletableFutureUtil {

  def failedFuture[T](e: Throwable): CompletableFuture[T] = {
    val future = new CompletableFuture[T]
    future.completeExceptionally(e)
    future
  }

}
