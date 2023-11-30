package objects

import org.openrndr.draw.Drawer

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
}

fun Drawer.print(matrix: Matrix) {
    matrix.rgbs.forEachIndexed { x, otherRgbs ->
        otherRgbs.filterNotNull().forEachIndexed { y, rgb ->
            this.fill(rgb)
            this.point(x.toDouble(), y.toDouble())
        }
    }
}