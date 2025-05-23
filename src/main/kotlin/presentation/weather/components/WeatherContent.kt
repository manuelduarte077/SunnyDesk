package presentation.weather.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import domain.model.Weather
import domain.model.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import presentation.util.ImageDownloader

@Composable
fun WeatherContent(weatherInfo: WeatherInfo) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Current weather
        CurrentWeatherCard(weather = weatherInfo.currentWeather)
        
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        
        // Forecast
        Text(
            text = "5-Day Forecast",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(weatherInfo.forecast) { forecastItem ->
                ForecastCard(weather = forecastItem)
            }
        }
    }
}

@Composable
fun CurrentWeatherCard(weather: Weather) {
    var weatherIcon by remember { mutableStateOf<ImageBitmap?>(null) }
    
    LaunchedEffect(weather.iconUrl) {
        withContext(Dispatchers.IO) {
            try {
                weatherIcon = ImageDownloader.downloadImage(weather.iconUrl)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weather.condition,
                style = MaterialTheme.typography.titleLarge
            )
            
            weatherIcon?.let {
                Image(
                    bitmap = it,
                    contentDescription = weather.condition,
                    modifier = Modifier.padding(8.dp)
                )
            }
            
            Text(
                text = "${weather.temperature}°C",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Text(
                text = "Feels like: ${weather.feelsLike}°C",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun ForecastCard(weather: Weather) {
    var weatherIcon by remember { mutableStateOf<ImageBitmap?>(null) }
    
    LaunchedEffect(weather.iconUrl) {
        withContext(Dispatchers.IO) {
            try {
                weatherIcon = ImageDownloader.downloadImage(weather.iconUrl)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    Card(
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weather.condition,
                style = MaterialTheme.typography.bodyMedium
            )
            
            weatherIcon?.let {
                Image(
                    bitmap = it,
                    contentDescription = weather.condition,
                    modifier = Modifier.padding(4.dp)
                )
            }
            
            Text(
                text = "${weather.temperature}°C",
                style = MaterialTheme.typography.bodyLarge
            )
            
            weather.chanceOfRain?.let {
                Text(
                    text = "Rain: ${it.toInt()}%",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
