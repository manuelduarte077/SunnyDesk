package domain.model

data class Weather(
    val condition: String,
    val iconUrl: String,
    val temperature: Double,
    val feelsLike: Double,
    val chanceOfRain: Double? = null,
)

data class WeatherInfo(
    val currentWeather: Weather,
    val forecast: List<Weather>,
)
