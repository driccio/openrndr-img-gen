package algorithms

import mu.KotlinLogging
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noise.Random
import org.openrndr.math.Polar
import org.openrndr.math.Vector2
import java.security.SecureRandom

// Fully inspired by https://openrndr.discourse.group/t/openrndr-processing-noise-fields-leaving-trails/215

private val logger = KotlinLogging.logger {}

fun Program.waves(iterations: Int, nbOfPoints: Int) {
    logger.info("Waves configuration :")
    logger.info("- iterations: $iterations")
    logger.info("- nb of points: $nbOfPoints")

    val zoom = 0.02
    val waveLength = (drawer.bounds.width * 1.5).toInt()//kotlin.random.Random.nextInt(300, 1000)

    val random = SecureRandom()
    val positions = (0..nbOfPoints).map {
        Vector2(
            0.0,
            random.nextDouble(0.0, drawer.bounds.width)
        )
    }

    drawer.fill = ColorRGBa.WHITE.copy(alpha = 0.01)

    repeat(iterations) {
        val time = seconds + (it)

        positions.forEach { position ->
            val pos = position.copy(y = position.y + kotlin.random.Random.nextDouble(-5.0, 5.0))

            val points = generateSequence(pos) {
                    it + Polar(
                        180 * Random.simplex(it.vector3(z = time) * zoom)
                    ).cartesian
                }
                .take(waveLength)
                .toList()

            drawer.points(points)
        }

        logger.debug("Progression: ${it/iterations.toDouble() * 100}%")
    }
}