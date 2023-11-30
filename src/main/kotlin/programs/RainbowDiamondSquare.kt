package programs

import algorithms.diamondSquare
import objects.Matrix
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

        backgroundColor = ColorRGBa.WHITE

        extend {
            drawer.print(matrix)
        }
    }
}

