package dynama.mapper

sealed trait QueryOrder

case object Asc extends QueryOrder

case object Desc extends QueryOrder
