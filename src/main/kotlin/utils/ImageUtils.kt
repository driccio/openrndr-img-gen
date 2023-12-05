package utils

import org.openrndr.draw.ColorBuffer
import org.openrndr.draw.ImageFileFormat
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

fun BufferedImage.toColorBuffer(): ColorBuffer {
    return ByteArrayOutputStream().use { output ->
        ImageIO.write(this, "png", output)

        return@use ByteArrayInputStream(output.toByteArray()).use { input ->
            ColorBuffer.fromStream(input, ImageFileFormat.PNG)
        }
    }
}