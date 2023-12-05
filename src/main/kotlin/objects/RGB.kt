package objects

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

data class RGB(
    val r: Int = Random.nextInt(255),
    val g: Int = Random.nextInt(255),
    val b: Int = Random.nextInt(255),
    val alpha: Double = 1.0
) {
    val intRGB: Int by lazy {
        (alpha * 255).toInt() and 0xFF shl 24 or
                (r and 0xFF shl 16) or
                (g and 0xFF shl 8) or
                (b and 0xFF shl 0)
    }

    companion object {
        val WHITE = RGB(255, 255, 255, 1.0)
        val BLACK = RGB(0, 0, 0, 1.0)
    }

    private fun vary(color: Int, seed: Int) = min(max(0, color + Random.nextInt(-seed, seed)), 255)

    fun vary(seed: Int) = RGB(
        vary(this.r, seed),
        vary(this.g, seed),
        vary(this.b, seed),
        this.alpha
    )

    fun median(other: RGB): RGB = RGB(
        (this.r + other.r) / 2,
        (this.g + other.g) / 2,
        (this.b + other.b) / 2,
        this.alpha
    )
}

fun Array<RGB>.median(): RGB = this.reduce(RGB::median)

fun Drawer.fill(rgb: RGB) {
    this.fill = rgb.toColorRGBa()
}

fun RGB.toColorRGBa(): ColorRGBa = ColorRGBa(
    this.r / 255.0,
    this.g / 255.0,
    this.b / 255.0,
    this.alpha
)
