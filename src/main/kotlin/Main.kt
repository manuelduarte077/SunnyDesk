

import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.IntSize

const val API_KEY = "9f1ad3d2cda545c281264554210212"

fun main() = Window(
  title = "Sunny Desk",
  size = IntSize(800, 700),
) {
  val repository = Repository(API_KEY)

  MaterialTheme {
    WeatherScreen(repository)
  }
}
