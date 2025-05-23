import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.remote.WeatherApi
import data.repository.WeatherRepositoryImpl
import domain.usecase.GetWeatherUseCase
import presentation.weather.WeatherScreen
import presentation.weather.WeatherViewModel

const val API_KEY = "b583b32d2e564eee82d93301252305"

@Composable
@Preview
fun App() {
    val weatherApi = WeatherApi(API_KEY)
    val weatherRepository = WeatherRepositoryImpl(weatherApi)
    val getWeatherUseCase = GetWeatherUseCase(weatherRepository)
    val weatherViewModel = WeatherViewModel(getWeatherUseCase)

    MaterialTheme {
        WeatherScreen(viewModel = weatherViewModel)
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SunnyDesk - Weather App"
    ) {
        App()
    }
}
