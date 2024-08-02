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
abstract class ProjectComponent<TSettings: Any>(
    val name: String,
    rootPath: Path,
) {
    val rootPath: Path = rootPath.toAbsolutePath()

    lateinit var settings: TSettings
        protected set

    val configPath: String = name

    val defaultConfigPath: Path =
        rootPath.resolve(Path.of("gmd.conf"))

    abstract fun initialize()

    abstract fun build()


    fun mergeConfig(vararg configFiles: String): Config =
        mergeConfig(
            configFiles = configFiles.map {
                rootPath.resolve(it)
            }.toTypedArray()
        )

    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        inline fun <reified T> loadSettings(path: String, vararg configFiles: Path): T? =
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