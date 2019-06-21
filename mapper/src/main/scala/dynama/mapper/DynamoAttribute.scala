package dynama.mapper

import scala.annotation.StaticAnnotation
import scala.annotation.meta.field

@field
final class DynamoAttribute(name: String) extends StaticAnnotation
