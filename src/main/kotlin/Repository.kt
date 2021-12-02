import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

class Repository(
    private val apiKey: String,
    private val client: HttpClient = defaultHttpClient,
) {
    suspend fun getWeatherForCity(city: String): WeatherResponse =
        client.get(
            "https://api.weatherapi.com/v1/forecast.json" +
                    "?key=$apiKey" +
                    "&q=$city" +
                    "&days=5" +
                    "&aqi=no" +
                    "&alerts=no"
        )

    companion object {
        val defaultHttpClient = HttpClient(CIO) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    json = kotlinx.serialization.json.Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }
}
