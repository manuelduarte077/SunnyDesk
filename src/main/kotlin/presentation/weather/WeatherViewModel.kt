package presentation.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import domain.model.WeatherInfo
import domain.usecase.GetWeatherUseCase
import domain.util.Result
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun getWeatherForCity(city: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            when (val result = getWeatherUseCase(city)) {
                is Result.Success -> {
                    state = state.copy(
                        weatherInfo = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Result.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.exception.message ?: "An unexpected error occurred"
                    )
                }

                is Result.Loading -> {
                    state = state.copy(isLoading = true)
                }
            }
        }
    }
}

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
