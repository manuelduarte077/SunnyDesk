package domain.repository

import domain.model.WeatherInfo
import domain.util.Result

interface WeatherRepository {
    suspend fun getWeatherForCity(city: String): Result<WeatherInfo>
}
