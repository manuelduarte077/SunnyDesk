import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import org.jetbrains.skia.Image as SkiaImage

object ImageDownloader {
    private val imageClient = HttpClient(CIO)

    suspend fun downloadImage(url: String): ImageBitmap {
        val response = imageClient.get(url)
        val imageBytes = response.readBytes()
        return SkiaImage.makeFromEncoded(imageBytes).toComposeImageBitmap()
    }
}