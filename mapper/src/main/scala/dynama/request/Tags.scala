package dynama.request

object Tags {

  trait Tag[+U]

  type @@[+T, +U] = T with Tag[U]
  type Tagged[+T, +U] = T with Tag[U]

  implicit class Tagger[T](val t: T) extends AnyVal {
    def taggedWith[U]: T @@ U = t.asInstanceOf[T @@ U]
  }

}
