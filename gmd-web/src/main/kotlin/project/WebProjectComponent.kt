package project

import java.nio.file.Path

class WebProjectComponent(rootPath: Path) : ProjectComponent<WebProjectSettings>(
    name = NAME,
    rootPath = rootPath,
) {
    override fun initialize() {
        settings = loadSettings(name, defaultConfigPath) ?: WebProjectSettings()
    }

    companion object {
        const val NAME = "web"
    }
}