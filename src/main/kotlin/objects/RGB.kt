package objects

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import kotlin.random.Random

data class RGB(
    val r: Int = Random.nextInt(255),
    val g: Int = Random.nextInt(255),
    val b: Int = Random.nextInt(255),
    val alpha: Double = 1.0
) {
    companion object {
        val WHITE = RGB(255, 255, 255, 1.0)
        val BLACK = RGB(0, 0, 0, 1.0)
    }

    fun vary(seed: Int) = RGB(
        (this.r + Random.nextInt(-seed, seed)) % 256,
        (this.g + Random.nextInt(-seed, seed)) % 256,
        (this.b + Random.nextInt(-seed, seed)) % 256,
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
