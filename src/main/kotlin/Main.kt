import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

const val API_KEY = "b583b32d2e564eee82d93301252305"

@Composable
@Preview
fun App() {
    val repository = Repository(API_KEY)

    MaterialTheme {
        WeatherScreen(repository)
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
