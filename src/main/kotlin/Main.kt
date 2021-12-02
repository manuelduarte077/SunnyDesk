
import androidx.compose.desktop.Window
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.launch

private const val API_KEY = ""

fun main() = Window {
  var text by remember { mutableStateOf("Hello, World!") }

  MaterialTheme {
    Button(onClick = {
      text = "Hello, Desktop!"
    }) {
      Text(text)
    }
  }
}
