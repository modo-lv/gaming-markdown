package project

import build.Builder
import build.FromMarkdown
import build.workers.ExplicitIdWorker
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigParseOptions
import com.typesafe.config.ConfigSyntax
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import java.nio.file.Path
import kotlin.io.path.*

/**
 * Manages fundamental, common project data.
 *
 * More specialized projects inherit from this and add their own functionality on top.
 */
open class CoreProject(rootFilePath: Path) {
    val rootFilePath = rootFilePath.absolute()

    lateinit var coreSettings: CoreProjectSettings

    var configFilePath: Path = rootFilePath.resolve(Path.of("gmd.conf"))

    /**
     * Short name of the project.
     *
     * Can be set explicitly in config, otherwise inferred from the name of the directory the project is in.
     */
    var projectName: String = rootFilePath.fileName.nameWithoutExtension

    /**
     * All pages in the project, parsed in alphabetical order (by full path, including subfolders).
     *
     * Runs the parsing on first access.
     */
    val pages: List<Page> by lazy {
        val builder = Builder.create(
            ExplicitIdWorker::class
        )
        val dir = rootFilePath.resolve(coreSettings.dirs.pages)
        dir.walk(PathWalkOption.INCLUDE_DIRECTORIES)
            .sortedBy { it.pathString }
            .filter { it.pathString.endsWith(".md") }
            .map { file ->
                Page(
                    filePath = file,
                    document = FromMarkdown(file.readText()).toDocument().let(builder::build)
                )
            }
            .toList()
    }

    /**
     * Initialize the project: load settings, perform basic sanity checks etc.
     */
    open fun initialize() {
        coreSettings =
            loadSettings<CoreProjectSettings>("core", configFilePath)
                ?.also { settings ->
                    settings.name?.also { this.projectName = it }
                    settings.labels.forEach {
                        it.value.key = it.key
                    }
                }
                ?: CoreProjectSettings()
    }


    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        inline fun <reified T> loadSettings(path: String, vararg configFiles: Path): T? =
            mergeConfig(*configFiles)
                .takeIf { it.hasPath(path) }
                ?.getConfig(path)
                ?.let { config -> Hocon.Default.decodeFromConfig<T>(config) }

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