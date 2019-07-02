package dynama.mapper

case class DynamoTable[A](name: String)
                         (implicit itemConverter: ItemConverter[A],
                          keyDefinition: KeyDefinition[A])
