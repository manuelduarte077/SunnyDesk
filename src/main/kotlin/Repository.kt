import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class Repository(
    private val apiKey: String,
    private val client: HttpClient = defaultHttpClient,
) {
    private val transformer = WeatherTransformer()
    private val json = Json { ignoreUnknownKeys = true }

    private suspend fun getWeatherForCity(city: String): WeatherResponse {
        val response = client.get(
            "https://api.weatherapi.com/v1/forecast.json" +
                    "?key=$apiKey" +
                    "&q=$city" +
                    "&days=5" +
                    "&aqi=no" +
                    "&alerts=no"
        )
        
        val responseText = response.bodyAsText()
        
        // Check if the response contains an error
        if (responseText.contains("\"error\":")) {
            val jsonElement = json.parseToJsonElement(responseText)
            val errorObject = jsonElement.jsonObject["error"]?.jsonObject
            val errorMessage = errorObject?.get("message")?.jsonPrimitive?.content
                ?: "Unknown API error"
            throw Exception("API Error: $errorMessage")
        }
        
        return json.decodeFromString<WeatherResponse>(responseText)
    }

    suspend fun weatherForCity(city: String): Lce<WeatherResults> {
        return try {
            val result = getWeatherForCity(city)
            val content = transformer.transform(result)
            Lce.Content(content)
        } catch (e: Exception) {
            e.printStackTrace()
            Lce.Error(e)
        }
    }

    companion object {
        val defaultHttpClient = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }
}