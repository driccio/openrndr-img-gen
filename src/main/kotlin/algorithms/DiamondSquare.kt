package algorithms

import objects.Matrix
import objects.RGB
import objects.median

fun Matrix.diamondSquare(diamondVariation: Int, squareVariation: Int): Matrix {
    val matrixSize: Int = this.xSize
    var step: Int = matrixSize - 1

    this
        .set(0, 0, RGB())
        .set(0, matrixSize - 1, RGB())
        .set(matrixSize - 1, 0, RGB())
        .set(matrixSize - 1, matrixSize - 1, RGB())

    while (step > 1) {
        val centroidStep: Int = step / 2

        // Diamond
        for (x in centroidStep until matrixSize step step) {
            for (y in centroidStep until matrixSize step step) {
                this.set(x, y, this.diamond(x, y, centroidStep).median().vary(diamondVariation))
            }
        }

        // Square
        var d = 0

        for (x in 0 until matrixSize step centroidStep) {
            if (d == 0) {
                d = centroidStep;
            } else {
                d = 0;
            }

            for (y in d until matrixSize step step) {
                var rgbs: Array<RGB> = arrayOf()

                if (x >= centroidStep) {
                    rgbs += this.get(x - centroidStep, y)!!
                }

                if (x + centroidStep < matrixSize) {
                    rgbs += this.get(x + centroidStep, y)!!
                }

                if (y >= centroidStep) {
                    rgbs += this.get(x, y - centroidStep)!!
                }

                if (y + centroidStep < matrixSize) {
                    rgbs += this.get(x, y + centroidStep)!!
                }

                this.set(x, y, rgbs.median().vary(squareVariation))
            }
        }

        step = centroidStep;
    }

    return this
}

fun Matrix.diamond(x: Int, y: Int, step: Int): Array<RGB> = arrayOf(
    this.get(x-step, y-step)!!,
    this.get(x+step, y-step)!!,
    this.get(x-step, y+step)!!,
    this.get(x+step, y+step)!!
)