package algorithms

import mu.KotlinLogging
import org.openrndr.Program
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noise.Random
import org.openrndr.math.Polar
import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import java.security.SecureRandom
import java.time.Clock

// Fully inspired by https://openrndr.discourse.group/t/openrndr-processing-noise-fields-leaving-trails/215

private val logger = KotlinLogging.logger {}


// simplex blur : it 200, pts 100, zoom 0.02
// cubic blur : it 100, pts 60, zoom 0.02 à 0.05
// value blur : it 100, pts 60, zoom 0.02 à 0.05

data class WaveConfiguration(val iterations: Int,
                             val nbOfPoints: Int,
                             val zoom: Double,
                             val randomAlgorithm: (Vector3) -> Double)

fun Program.waves(configuration: WaveConfiguration ) {
    logger.info("Waves configuration : $configuration")

    // We want non-deterministic random.
    Random.seed = "" + Clock.systemUTC().instant().toEpochMilli()

    val waveLength = (drawer.bounds.width * 1.5).toInt()//kotlin.random.Random.nextInt(300, 1000)

    val positions = (0..configuration.nbOfPoints).map {
        Vector2(
            0.0,
            Random.double(0.0, drawer.bounds.width)
        )
    }

    drawer.fill = ColorRGBa.WHITE.copy(alpha = 0.01)

    repeat(configuration.iterations) {
        val time = seconds + (it)

        positions.forEach { position ->
            val pos = position.copy(y = position.y + Random.double(-5.0, 5.0))

            val points = generateSequence(pos) {
                it + Polar(
                    180 * configuration.randomAlgorithm(it.vector3(z = time) * configuration.zoom)
                ).cartesian
            }
                .take(waveLength)
                .toList()

            drawer.points(points)
        }

        logger.debug("Progression: ${it / configuration.iterations.toDouble() * 100}%")
    }
}