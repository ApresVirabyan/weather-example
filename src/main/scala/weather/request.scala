package weather

import weather.model.Request
/**
 * User: Apres Virabyan
 * Company: DataArt
 * Date: 5/21/2022
 * Time: 12:22 PM
 */
object request {
  sealed trait WeatherRequest extends Request

  final case class HistoryRequest(resource: String, parameters: Map[String, String]) extends WeatherRequest{
    val endpoint: Option[String] = Some("history")
  }

  final case class CurrentRequest(resource: String, parameters: Map[String, String]) extends WeatherRequest{
    val endpoint: Option[String] = None
  }
}
