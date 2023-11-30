package programs

import algorithms.diamondSquare
import algorithms.waves
import objects.Matrix
import objects.print
import org.openrndr.PresentationMode
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.ImageFileFormat
import org.openrndr.extra.compositor.*
import org.openrndr.extra.fx.blend.SourceAtop
import org.openrndr.extra.fx.blur.ApproximateGaussianBlur
import utils.filename
import kotlin.io.path.Path
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
        backgroundColor = ColorRGBa.WHITE

        val composite = compose {
            layer {
                val matrix: Matrix = Matrix(matrixSize)
                    .diamondSquare(diamondVariation, squareVariation)

                draw {
                    drawer.print(matrix)
                }
            }

            layer {
                blend(SourceAtop())
                //blend(DestinationAtop())

                draw {
                    waves(matrixSize)
                }

                post(ApproximateGaussianBlur()) {
                    window = 1
                    sigma = 0.2
                }
            }
        }

        extend {
            // draw the composite
            composite.draw(drawer)

            composite.result.saveToFile(
                Path("./generated/" + filename("rainbow-waves", "png")).toFile(),
                ImageFileFormat.PNG
            )
        }
    }
}


