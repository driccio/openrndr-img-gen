package objects

import org.openrndr.draw.Drawer
import utils.toColorBuffer
import java.awt.image.BufferedImage


data class Matrix(
    val xSize: Int,
    val ySize: Int = xSize,
    val rgb: RGB? = null
) {
    internal val rgbs: Array<Array<RGB?>> = Array(xSize){ Array(ySize) { rgb ?: RGB() } }

    fun get(x: Int, y: Int): RGB? = this.rgbs[x][y]

    fun set(x: Int, y: Int, value: RGB?): Matrix {
        this.rgbs[x][y] = value
        return this
    }

    fun exists(x: Int, y: Int): Boolean = x < xSize && y < ySize && this.get(x, y) != null
}

fun Drawer.print(matrix: Matrix) {
    val image = BufferedImage(matrix.xSize, matrix.ySize, BufferedImage.TYPE_INT_ARGB)

    for (x in 0 until matrix.xSize) {
        for (y in 0 until matrix.ySize) {
            val rgb = matrix.get(x, y)

            if (rgb != null) {
                image.setRGB(x, y, rgb.intRGB)
            }
        }
    }

    this.image(image.toColorBuffer())
}