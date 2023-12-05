package programs

import algorithms.diamondSquare
import objects.Matrix
import objects.RGB
import objects.print
import org.openrndr.PresentationMode
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.colorBuffer
import org.openrndr.drawImage
import utils.saveAsImage
import kotlin.math.pow

fun main() = application {
    val degreeX = 10
    val degreeY = 10
    val diamondVariation = 28
    val squareVariation = 28
    val xSize = 2.0.pow(degreeX.toDouble()).toInt() + 1
    val ySize = 2.0.pow(degreeY.toDouble()).toInt() + 1

    configure {
        width = xSize
        height = ySize
    }

    program {
        window.presentationMode = PresentationMode.MANUAL

        val matrix: Matrix = Matrix(xSize, ySize)
            .diamondSquare(diamondVariation, squareVariation)

//         You can control the corners colors
//         val matrix: Matrix = Matrix(xSize, ySize)
//                    .diamondSquare(diamondVariation, squareVariation) {
//                        it
//                            .set(0, 0, RGB(255, 0, 0))
//                            .set(0, it.ySize - 1, RGB(0, 255, 0))
//                            .set(it.xSize - 1, 0, RGB(0, 0, 255))
//                            .set(it.xSize - 1, it.ySize - 1, RGB(255, 255, 255))
//                    }

        backgroundColor = ColorRGBa.WHITE

        extend {
            val colorBuffer = drawImage(
                width = matrix.xSize,
                height = matrix.ySize
            ) {
                drawer.print(matrix)
            }

            drawer.image(colorBuffer)

            saveAsImage(colorBuffer, "diamond-square", null)
        }
    }
}

