package dynama.mapper

case class SortKey[A: AttributeConverter](name: String) {
  val converter: AttributeConverter[A] = implicitly[AttributeConverter[A]]
}

object SortKey {

  implicit class SortKeyOps[A: AttributeConverter](key: SortKey[A]) {
    def ===(value: A): Equals[A] = Equals(key, value)

    def <(value: A): LessThan[A] = LessThan(key, value)

    def <=(value: A): LessThanOrEqual[A] = LessThanOrEqual(key, value)

    def >(value: A): BiggerThan[A] = BiggerThan(key, value)

    def >=(value: A): BiggerThanOrEqual[A] = BiggerThanOrEqual(key, value)

    def between(value1: A, value2: A): Between[A] = Between(key, value1, value2)

    def between(value: (A, A)): Between[A] = Between(key, value._1, value._2)
  }

  implicit class StringSortKeyOps(key: SortKey[String]) extends SortKeyOps[String](key) {
    def beginsWith(value: String): BeginsWith = BeginsWith(key, value)
  }

}

