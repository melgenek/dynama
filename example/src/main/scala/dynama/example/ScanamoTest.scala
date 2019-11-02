package dynama.example

object ScanamoTest extends App {

  import com.gu.scanamo._
  import com.gu.scanamo.syntax._


  case class Station(line: String, name: String, zone: Int)

  val stationTable = Table[Station]("Station")

  private val value1 = 'zone < 8 and 'zone > 10
  println(value1)

  private val value = value1 and 'cool > 10
  val ops = for {
    _ <- stationTable.putAll(Set(
      Station("Metropolitan", "Chalfont & Latimer", 8),
      Station("Metropolitan", "Chorleywood", 7),
      Station("Metropolitan", "Rickmansworth", 7),
      Station("Metropolitan", "Croxley", 7),
      Station("Jubilee", "Canons Park", 5)
    ))
    filteredStations <-
      stationTable
        .filter(value)
        .query('line -> "Metropolitan" and ('name beginsWith "C"))
  } yield filteredStations

  //
  //  import slick.jdbc.H2Profile.api._
  //
  //
  //  class Coffees(tag: Tag) extends Table[(String, Int, Double, Int, Int)](tag, "COFFEES") {
  //    def name = column[String]("COF_NAME", O.PrimaryKey)
  //
  //    def supID = column[Int]("SUP_ID")
  //
  //    def price = column[Double]("PRICE")
  //
  //    def sales = column[Int]("SALES", O.Default(0))
  //
  //    def total = column[Int]("TOTAL", O.Default(0))
  //
  //    def * = (name, supID, price, sales, total)
  //  }
  //
  //  val coffees = TableQuery[Coffees]
  //
  //  val q = coffees.filter(r => r.price > 8.0).map(_.name)
  //

}


