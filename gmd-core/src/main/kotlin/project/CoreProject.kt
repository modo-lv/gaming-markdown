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
import java.nio.file.FileSystem
import java.nio.file.Path
import kotlin.io.path.*

/**
 * Manages fundamental, common project data.
 *
 * More specialized projects inherit from this and add their own functionality on top.
 */
open class CoreProject(rootFilePath: Path) {
    val rootFilePath: Path = rootFilePath.absolute().normalize()

    val fs: FileSystem = this.rootFilePath.fileSystem

    lateinit var coreSettings: CoreProjectSettings

    var configFilePath: Path = this.rootFilePath.resolve(fs.getPath("gmd.conf"))

    /**
     * Short name of the project.
     *
     * Can be set explicitly in config, otherwise inferred from the name of the directory the project is in.
     */
    var name: String = this.rootFilePath.fileName.nameWithoutExtension

    fun pathTo(path: String): Path = rootFilePath.resolve(path)
    fun pathTo(path: Path): Path = rootFilePath.resolve(path)

    /**
     * All pages in the project, parsed in alphabetical order (by full path, including subfolders).
     *
     * Must be filled by calling [parsedPages].
     */
    private var pages: List<Page>? = null

    /**
     * Returns the list of [Page]s for this project, in alphabetical order (by full path, including subfolders).
     *
     * If the list has not been populated yet, will run the parsing process and populate it.
     * It can be an empty list if the project has no page files.
     *
     * @param recreate Rerun the parsing even if a page list already exists?
     */
    fun parsedPages(recreate: Boolean = false): List<Page> {
        if (recreate || pages == null) {
            val builder = Builder.create(
                ExplicitIdWorker::class
            )
            val dir = rootFilePath.resolve(coreSettings.dirs.pages)
            pages = dir.walk(PathWalkOption.INCLUDE_DIRECTORIES)
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
        return pages!!
    }

    /**
     * Initialize the project: load settings, perform basic sanity checks etc.
     */
    open fun initialize() {
        coreSettings =
            loadSettings<CoreProjectSettings>("core", configFilePath)
                ?.also { settings ->
                    settings.name?.also { this.name = it }
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
                    if (!configPath.exists())
                        return@foldRight config
                    config.withFallback(
                        // Can't use `parseFile` because it required `File`, which isn't supported by Jimfs
                        ConfigFactory.parseString(
                            configPath.readText(),
                            ConfigParseOptions.defaults().setAllowMissing(true).setSyntax(ConfigSyntax.CONF)
                        )
                    )
                }
    }
}