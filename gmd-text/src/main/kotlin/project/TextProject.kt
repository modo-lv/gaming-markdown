package project

import project.Project.Companion.loadConfig
import java.nio.file.Path

class TextProject(val core: CoreProject) : Project<TextProjectSettings> {
    override val rootPath: Path = core.rootPath

    override lateinit var settings: TextProjectSettings
        private set

    fun initialize(): TextProject {
        core.initialize()
        settings = loadConfig("web", defaultConfig)
            ?: TextProjectSettings()
        return this
    }
}