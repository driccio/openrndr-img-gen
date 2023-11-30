package algorithms

import objects.Matrix
import objects.RGB
import objects.median
import kotlin.math.max

private fun initCorners(matrix: Matrix) {
    matrix
        .set(0, 0, RGB())
        .set(0, matrix.ySize - 1, RGB())
        .set(matrix.xSize - 1, 0, RGB())
        .set(matrix.xSize - 1, matrix.ySize - 1, RGB())
}

fun Matrix.diamondSquare(diamondVariation: Int, squareVariation: Int,
                         initCorners: (Matrix) -> Unit = ::initCorners): Matrix {
    var step: Int = max(xSize, ySize) - 1

    initCorners(this)

    while (step > 1) {
        val centroidStep: Int = step / 2

        // Diamond
        for (x in centroidStep until xSize step step) {
            for (y in centroidStep until ySize step step) {
                this.set(x, y, this.diamond(x, y, centroidStep).median().vary(diamondVariation))
            }
        }

        // Square
        var d = 0

        for (x in 0 until xSize step centroidStep) {
            if (d == 0) {
                d = centroidStep;
            } else {
                d = 0;
            }

            for (y in d until ySize step step) {
                var rgbs: Array<RGB> = arrayOf()

                if (x >= centroidStep) {
                    rgbs += this.get(x - centroidStep, y)!!
                }

                if (x + centroidStep < xSize) {
                    rgbs += this.get(x + centroidStep, y)!!
                }

                if (y >= centroidStep) {
                    rgbs += this.get(x, y - centroidStep)!!
                }

                if (y + centroidStep < ySize) {
                    rgbs += this.get(x, y + centroidStep)!!
                }

                this.set(x, y, rgbs.median().vary(squareVariation))
            }
        }

        step = centroidStep;
    }

    return this
}

fun Matrix.diamond(x: Int, y: Int, step: Int): Array<RGB> {
    var rgbs: Array<RGB> = arrayOf()

    if (this.exists(x-step, y-step)) {
        rgbs += this.get(x-step, y-step)!!
    }

    if (this.exists(x+step, y-step)) {
        rgbs += this.get(x+step, y-step)!!
    }

    if (this.exists(x-step, y+step)) {
        rgbs += this.get(x-step, y+step)!!
    }

    if (this.exists(x+step, y+step)) {
        rgbs += this.get(x+step, y+step)!!
    }

    return rgbs
}