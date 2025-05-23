package data.repository

import data.mapper.toWeatherInfo
import data.remote.WeatherApi
import domain.model.WeatherInfo
import domain.repository.WeatherRepository
import domain.util.Result

class WeatherRepositoryImpl(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherForCity(city: String): Result<WeatherInfo> {
        return try {
            val weatherResponse = api.getWeatherForCity(city)
            Result.Success(weatherResponse.toWeatherInfo())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
    }
}
