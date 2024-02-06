package internal

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging

inline fun <reified T: Any> T.getLogger(): KLogger =
    KotlinLogging.logger(T::class.qualifiedName!!)