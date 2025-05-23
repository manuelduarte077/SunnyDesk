package presentation.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.weather.components.ErrorContent
import presentation.weather.components.LoadingContent
import presentation.weather.components.WeatherContent

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel
) {
    val state = viewModel.state
    var queriedCity by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                    viewModel.getWeatherForCity(queriedCity)
                }
            ) {
                Icon(Icons.Outlined.Search, "Search")
            }
        }

        when {
            state.isLoading -> LoadingContent()
            state.error != null -> ErrorContent(message = state.error)
            state.weatherInfo != null -> WeatherContent(weatherInfo = state.weatherInfo)
        }
    }
}
