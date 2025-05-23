import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment

@Composable
fun WeatherScreen(repository: Repository) {
    var queriedCity by remember { mutableStateOf("") }
    var weatherState by remember { mutableStateOf<Lce<WeatherResults>?>(null) }
    val scope = rememberCoroutineScope()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = queriedCity,
                onValueChange = { queriedCity = it },
                modifier = Modifier
                    .padding(end = 16.dp)
                    .weight(1f),
                placeholder = { Text("Any city, really...") },
                label = { Text(text = "Search for a city") },
                leadingIcon = { Icon(Icons.Filled.LocationOn, "Location") },
            )
            Button(
                onClick = {
                    weatherState = Lce.Loading
                    scope.launch {
                        weatherState = repository.weatherForCity(queriedCity)
                    }
                }
            ) {
                Icon(Icons.Outlined.Search, "Search")
            }
        }
        when (val state = weatherState) {
            is Lce.Loading -> LoadingUI()
            is Lce.Error -> ErrorUI()
            is Lce.Content -> ContentUI(state.data)
            null -> Unit
        }
    }
}


@Composable
fun ContentUI(data: WeatherResults) {
    var imageState by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(data.currentWeather.iconUrl) {
        imageState = ImageDownloader.downloadImage(data.currentWeather.iconUrl)
    }

    Text(
        text = "Current weather",
        modifier = Modifier.padding(all = 16.dp),
        style = MaterialTheme.typography.titleMedium,
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 72.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = data.currentWeather.condition,
                style = MaterialTheme.typography.titleMedium,
            )

            imageState?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    contentDescription = null,
                    modifier = Modifier
                        .defaultMinSize(minWidth = 128.dp, minHeight = 128.dp)
                        .padding(top = 8.dp)
                )
            }

            Text(
                text = "Temperature in °C: ${data.currentWeather.temperature}",
                modifier = Modifier.padding(all = 8.dp),
            )
            Text(
                text = "Feels like: ${data.currentWeather.feelsLike}",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }

    Divider(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(all = 16.dp),
    )

    Text(
        text = "Forecast",
        modifier = Modifier.padding(all = 16.dp),
        style = MaterialTheme.typography.titleMedium,
    )
    LazyRow {
        items(data.forecast) { weatherCard ->
            ForecastUI(weatherCard)
        }
    }
}

@Composable
fun ForecastUI(weatherCard: WeatherCard) {
    var imageState by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(weatherCard.iconUrl) {
        imageState = ImageDownloader.downloadImage(weatherCard.iconUrl)
    }

    Card(modifier = Modifier.padding(all = 4.dp)) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = weatherCard.condition,
                style = MaterialTheme.typography.titleMedium
            )

            imageState?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    contentDescription = null,
                    modifier = Modifier
                        .defaultMinSize(minWidth = 64.dp, minHeight = 64.dp)
                        .padding(top = 8.dp)
                )
            }

            val chanceOfRainText = String.format(
                "Chance of rain: %.2f%%", weatherCard.chanceOfRain
            )

            Text(
                text = chanceOfRainText,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
fun ErrorUI() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Something went wrong, try again in a few minutes. ¯\\_(ツ)_/¯",
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 72.dp, vertical = 72.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error,
        )
    }
}

@Composable
fun LoadingUI() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .defaultMinSize(minWidth = 96.dp, minHeight = 96.dp)
        )
    }
}