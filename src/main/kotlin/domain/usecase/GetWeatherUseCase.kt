package domain.usecase

import domain.model.WeatherInfo
import domain.repository.WeatherRepository
import domain.util.Result

class GetWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(city: String): Result<WeatherInfo> {
        if (city.isBlank()) {
            return Result.Error(IllegalArgumentException("City name cannot be empty"))
        }
        return repository.getWeatherForCity(city)
    }
}
