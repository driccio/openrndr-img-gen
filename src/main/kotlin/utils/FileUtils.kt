package utils

import org.openrndr.draw.ColorBuffer
import java.time.Clock
import kotlin.io.path.Path

private val generatedImageFolderPath = Path("./generated")

private fun filename(prefix: String): String =
    "$prefix-${Clock.systemUTC().instant().epochSecond}"

fun saveAsImage(result: ColorBuffer, prefix: String, metadataAsString: String?) {
    val filename = filename(prefix)

    // Save image file
    result.saveToFile(generatedImageFolderPath.resolve("$filename.png").toFile())

    if (metadataAsString != null) {
        // Write metadata in a json file
        generatedImageFolderPath.resolve("$filename.txt").toFile().writeText(metadataAsString)
    }
}
