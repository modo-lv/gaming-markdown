package project

import project.Project.Companion.loadConfig
import java.nio.file.Path

class CoreProject(rootPath: Path) : Project<CoreProjectSettings> {
    override val rootPath: Path = rootPath.toAbsolutePath()

    override lateinit var settings: CoreProjectSettings
        private set

    /**
     * Initialize the project: load settings, perform basic sanity checks etc.
     */
    fun initialize(): CoreProject {
        settings = loadConfig<CoreProjectSettings>("core", defaultConfig)
            ?.takeIf { it.name.isNotBlank() }
            ?.also { settings ->
                settings.labels.forEach {
                    it.value.key = it.key
                }
            }
            ?: throw Exception("Project must have a configuration file with at least [core.name] set.")
        return this
    }
}