package algorithms

import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noise.Random
import org.openrndr.math.Polar
import org.openrndr.math.Vector2
import java.security.SecureRandom

// Fully inspired by https://openrndr.discourse.group/t/openrndr-processing-noise-fields-leaving-trails/215

fun Program.waves(matrixSize: Int) {
    val iterations = 500
    val nbOfPoints = 60
    val zoom = 0.02
    val length = (matrixSize * 1.5).toInt()//kotlin.random.Random.nextInt(300, 1000)

    val random = SecureRandom()
    val positions = (0..nbOfPoints).map {
        Vector2(
            0.0,
            random.nextDouble(0.0, matrixSize.toDouble())
        )
    }

    drawer.fill = ColorRGBa.WHITE.copy(alpha = 0.01)

    repeat(iterations) {
        val time = seconds + (it)

        positions.forEach { position ->
            var pos = position.copy(y = position.y + kotlin.random.Random.nextDouble(-10.0, 10.0))

            repeat(length) {
                if (drawer.bounds.contains(pos)) {
                    drawer.point(pos.x, pos.y)

                    pos += Polar(
                        180 * Random.simplex(pos.vector3(z = time) * zoom)
                    ).cartesian
                }
            }
        }
    }
}