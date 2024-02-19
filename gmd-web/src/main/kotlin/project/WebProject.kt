package project

import project.Project.Companion.loadConfig
import java.nio.file.Path

class WebProject(val core: CoreProject) : Project<WebProjectSettings> {
    override val rootPath: Path = core.rootPath

    override lateinit var settings: WebProjectSettings
        private set

    fun initialize(): WebProject {
        core.initialize()
        settings = loadConfig("web", defaultConfig)
            ?: WebProjectSettings()
        return this
    }
}