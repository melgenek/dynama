package dynama.mapper

import java.util.concurrent.CompletableFuture

import scala.concurrent.{Future, Promise}
import scala.language.higherKinds
import scala.util.{Failure, Success}

trait CompletableFutureConverter[F[_]] {

  def fromCompletableFuture[A](f: CompletableFuture[A]): F[A]

}

object CompletableFutureConverter {

  implicit def futureConverter: CompletableFutureConverter[Future] = new CompletableFutureConverter[Future] {
    override def fromCompletableFuture[A](f: CompletableFuture[A]): Future[A] = {
      val p = Promise[A]()
      f.whenComplete((value: A, e: Throwable) => {
        if (e == null) p.complete(Success(value))
        else p.complete(Failure(e))
      })
      p.future
    }
  }

}
