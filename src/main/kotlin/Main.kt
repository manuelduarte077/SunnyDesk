import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.IntSize

const val API_KEY = ""

fun main() = Window(
    title = "Sunny Desk (KT)",
    size = IntSize(800, 700),
) {
    val repository = Repository(API_KEY)

    MaterialTheme {
        WeatherScreen(repository)
    }
}
