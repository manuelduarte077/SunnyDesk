package presentation.util

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import org.jetbrains.skia.Image
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

object ImageDownloader {
    private val client = HttpClient(CIO)

    suspend fun downloadImage(url: String): ImageBitmap? {
        return try {
            val response = client.get(url)
            val bytes = response.readBytes()

            Image.makeFromEncoded(bytes).toComposeImageBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
