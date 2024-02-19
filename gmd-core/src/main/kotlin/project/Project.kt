package project

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigParseOptions
import com.typesafe.config.ConfigSyntax
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import java.nio.file.Path

/**
 * Base class for different project types.
 */
interface Project<TSettings> {
    /**
     * Absolute path to the project root dir.
     */
    val rootPath: Path

    val settings: TSettings

    val defaultConfig: Path
        get() = rootPath.resolve(Path.of("gmd.conf"))

    fun mergeConfig(vararg configFiles: String): Config =
        mergeConfig(
            configFiles = configFiles.map {
                rootPath.resolve(it)
            }.toTypedArray()
        )

    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        inline fun <reified T> loadConfig(path: String, vararg configFiles: Path): T? =
            mergeConfig(*configFiles)
                .takeIf { it.hasPath(path) }
                ?.getConfig(path)
                ?.let { Hocon.Default.decodeFromConfig<T>(it) }

        /**
         * @param configFiles Configuration files parsed and merged in order, later values overwriting earlier.
         */
        fun mergeConfig(vararg configFiles: Path): Config =
            configFiles
                .foldRight(ConfigFactory.empty()) { configPath, config ->
                    config.withFallback(
                        ConfigFactory.parseFile(
                            configPath.toFile(),
                            ConfigParseOptions.defaults().setAllowMissing(true).setSyntax(ConfigSyntax.CONF)
                        )
                    )
                }
    }
}