package project

import java.nio.file.Path

class WebProjectComponent(rootPath: Path) : ProjectComponent<WebProjectSettings>(
    componentType = NAME,
    rootPath = rootPath,
) {
    override fun initialize() {
        settings = loadSettings(componentType, defaultConfigPath) ?: WebProjectSettings()
    }

    companion object {
        const val NAME = "web"
    }
}