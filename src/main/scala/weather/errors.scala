package weather

/**
 * User: Apres Virabyan
 * Company: DataArt
 * Date: 5/21/2022
 * Time: 12:30 PM
 */
object errors {
  sealed abstract class WeatherError(message: String) extends Exception(message)

  final case class InternalError(message: String) extends WeatherError(message)
}
