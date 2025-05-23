package data.mapper

import data.remote.dto.Forecastday
import data.remote.dto.Hour
import data.remote.dto.WeatherResponse
import domain.model.Weather
import domain.model.WeatherInfo

fun WeatherResponse.toWeatherInfo(): WeatherInfo {
    val currentWeather = Weather(
        condition = current.condition.text,
        iconUrl = "https:" + current.condition.icon.replace("64x64", "128x128"),
        temperature = current.tempC,
        feelsLike = current.feelslikeC,
    )

    val forecast = forecast.forecastday.map { forecastDay ->
        Weather(
            condition = forecastDay.day.condition.text,
            iconUrl = "https:" + forecastDay.day.condition.icon,
            temperature = forecastDay.day.avgtempC,
            feelsLike = avgFeelsLike(forecastDay),
            chanceOfRain = avgChanceOfRain(forecastDay),
        )
    }

    return WeatherInfo(
        currentWeather = currentWeather,
        forecast = forecast
    )
}

private fun avgFeelsLike(forecastDay: Forecastday): Double =
    forecastDay.hour.map(Hour::feelslikeC).average()

private fun avgChanceOfRain(forecastDay: Forecastday): Double =
    forecastDay.hour.map(Hour::chanceOfRain).average()
