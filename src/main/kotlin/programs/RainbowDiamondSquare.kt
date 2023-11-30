package programs

import algorithms.diamondSquare
import objects.Matrix
import objects.RGB
import objects.print
import org.openrndr.PresentationMode
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import kotlin.math.pow

fun main() = application {
    val degree = 10
    val diamondVariation = 28
    val squareVariation = 28
    val matrixSize = 2.0.pow(degree.toDouble()).toInt() + 1

    configure {
        width = matrixSize
        height = matrixSize
    }

    program {
        window.presentationMode = PresentationMode.MANUAL

        val matrix: Matrix = Matrix(matrixSize)
            .diamondSquare(diamondVariation, squareVariation)

        // You can control the corners colors
        // val matrix: Matrix = Matrix(matrixSize)
        //            .diamondSquare(diamondVariation, squareVariation) {
        //                it
        //                    .set(0, 0, RGB(255, 0, 0))
        //                    .set(0, it.ySize - 1, RGB(0, 255, 0))
        //                    .set(it.xSize - 1, 0, RGB(0, 0, 255))
        //                    .set(it.xSize - 1, it.ySize - 1, RGB(255, 255, 255))
        //            }

        backgroundColor = ColorRGBa.WHITE

        extend {
            drawer.print(matrix)
        }
    }
}

