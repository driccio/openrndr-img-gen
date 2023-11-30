package utils

import java.time.Clock

fun filename(prefix: String, extension: String): String =
    "$prefix-${Clock.systemUTC().instant().epochSecond}.$extension"