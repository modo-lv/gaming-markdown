package project

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigParseOptions
import com.typesafe.config.ConfigSyntax
import java.nio.file.Path

/**
 * Base class for GMD projects.
 */
open class Project(rootPath: Path) {
    val rootPath: Path = rootPath.toAbsolutePath()

    /**
     * project.Project name, for display and logging purposes.
     */
    val name: String by lazy { settings.name }

    /**
     * Path to source Markdown pages within the project.
     */
    var pageDir: Path = Path.of("pages")

    private var _settings: ProjectSettings? = null
    var settings: ProjectSettings
        get() = _settings ?: replaceSettings(this.rootPath.resolve("gmd.conf"))
        protected set(value) {
            _settings = value
        }

    fun replaceSettings(vararg configFiles: Path): ProjectSettings {
        val newSettings = ProjectSettings()
        configFiles.forEach { configPath ->
            val conf = ConfigFactory.parseFile(
                configPath.toFile(),
                ConfigParseOptions.defaults().setAllowMissing(true).setSyntax(ConfigSyntax.CONF)
            ).ifConfig("core") { it } ?: ConfigFactory.empty()

            conf.ifString("name") { newSettings.name = it }
        }
        if (newSettings.name.isBlank())
            throw Exception("Project must have a name configured.")
        return newSettings
    }


    override fun toString(): String =
        "${this::class.simpleName}(" +
                "${this::rootPath.name}=${this.rootPath}, " +
                "${this::name.name}=${this.name}, " +
                "${this::pageDir.name}=${this.pageDir}" +
                ")"

    fun <T> Config.ifString(path: String, f: (String) -> T): T? =
        if (this.hasPath(path)) f(this.getString(path)) else null

    fun <T> Config.ifConfig(path: String, f: (Config) -> T): T? =
        if (this.hasPath(path)) f(this.getConfig(path)) else null
}