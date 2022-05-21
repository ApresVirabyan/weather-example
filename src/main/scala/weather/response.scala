package weather

import io.circe.generic.JsonCodec
import weather.errors.{InternalError, WeatherError}
import weather.model.Response

import java.time.ZonedDateTime

/**
 * User: Apres Virabyan
 * Company: DataArt
 * Date: 5/21/2022
 * Time: 12:29 PM
 */
object response {
  sealed trait WeatherResponse extends Response

  @JsonCodec
  final case class Current(
                            main: MainInfo,
                            wind: Wind,
                            clouds: Clouds
                          )

  @JsonCodec
  final case class MainInfo(
                             humidity: BigDecimal,
                             pressure: BigDecimal,
                             sea_level: Option[BigDecimal],
                             temp: BigDecimal,
                             temp_min: BigDecimal,
                             temp_max: BigDecimal
                           )

  @JsonCodec
  final case class Wind(
                         speed: BigDecimal,
                         gust: Option[BigDecimal]
                       )

  @JsonCodec
  final case class Weather(
                            main: MainInfo,
                            wind: Wind,
                            clouds: Clouds,
                            rain: Option[Rain],
                            snow: Option[Snow],
                            dt: BigInt
                          ) extends WeatherResponse

  @JsonCodec final case class Clouds(all: BigInt)
  @JsonCodec final case class Snow(`1h`: Option[BigDecimal], `3h`: Option[BigDecimal])
  @JsonCodec final case class Rain(`1h`: Option[BigDecimal], `3h`: Option[BigDecimal])

  @JsonCodec
  final case class History(cnt: BigInt, cod: String, list: List[Weather]) extends WeatherResponse {
    def pickCloseIn(dateTime: ZonedDateTime): Either[WeatherError, Weather] =
      pickClosestWeather(list, dateTime.toEpochSecond)
  }

  private[openweather] def pickClosestWeather(
                                               list: List[Weather],
                                               timestamp: Long
                                             ): Either[WeatherError, Weather] =
    if (timestamp < 1) Left(InternalError("Timestamp should be greater than 0"))
    else
      pickClosest(list, timestamp, (st: Weather) => (st.dt.toInt, st))
        .map(Right(_))
        .getOrElse(Left(InternalError("Server response has no weather stamps")))

  private[openweather] def pickClosest[A](
                                           list: List[A],
                                           index: Long,
                                           transform: A => (Int, A)
                                         ): Option[A] =
    list.map(transform).sortBy(x => Math.abs(index - x._1)).headOption.map(_._2)
}

