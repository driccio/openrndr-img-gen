package programs

import algorithms.WaveConfiguration
import algorithms.diamondSquare
import algorithms.waves
import objects.Matrix
import objects.print
import org.openrndr.PresentationMode
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.compositor.*
import org.openrndr.extra.fx.blend.SourceAtop
import org.openrndr.extra.fx.blur.ApproximateGaussianBlur
import org.openrndr.extra.noise.Random
import utils.saveAsImage
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
        backgroundColor = ColorRGBa.BLACK

        val waveConfiguration = WaveConfiguration(100, 80, 0.04, Random::simplex)

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
                    waves(waveConfiguration)
                }

                post(ApproximateGaussianBlur()) {
                    window = 1
                    sigma = 0.2
                }
            }
        }

        extend {
            composite.draw(drawer)

            saveAsImage(composite.result, "rainbow-waves", waveConfiguration.toString())
        }
    }
}
