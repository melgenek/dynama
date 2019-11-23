package dynama.client

import java.util.concurrent.CompletableFuture

import scala.concurrent.{Future, Promise}
import scala.language.higherKinds
import scala.util.{Failure, Success}

trait FLifter[F[_]] {

  def liftF[A](f: CompletableFuture[A]): F[A]

}

object FLifter {

  implicit def futureConverter: FLifter[Future] = new FLifter[Future] {
    override def liftF[A](f: CompletableFuture[A]): Future[A] = {
      val p = Promise[A]()
      f.whenComplete((value: A, e: Throwable) => {
        if (e == null) p.complete(Success(value))
        else p.complete(Failure(e))
      })
      p.future
    }
  }

}
