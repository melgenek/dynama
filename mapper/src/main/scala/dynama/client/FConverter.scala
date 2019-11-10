package dynama.client

import java.util.concurrent.CompletableFuture

import scala.concurrent.{Future, Promise}
import scala.language.higherKinds
import scala.util.{Failure, Success}

trait FConverter[F[_]] {

  def liftF[A](f: CompletableFuture[A]): F[A]

  def raiseError[A](e: Throwable): F[A]

}

object FConverter {

  implicit def futureConverter: FConverter[Future] = new FConverter[Future] {
    override def liftF[A](f: CompletableFuture[A]): Future[A] = {
      val p = Promise[A]()
      f.whenComplete((value: A, e: Throwable) => {
        if (e == null) p.complete(Success(value))
        else p.complete(Failure(e))
      })
      p.future
    }

    override def raiseError[A](e: Throwable): Future[A] = Future.failed(e)
  }

}
