package project

import java.nio.file.Path

class WebProjectComponent(rootPath: Path) : ProjectComponent<WebProjectSettings>(
    name = NAME,
    rootPath = rootPath,
) {
    override fun initialize() {
        settings = loadSettings(name, defaultConfigPath) ?: WebProjectSettings()
    }

    override fun build() {
        TODO("Not yet implemented")
    }

    companion object {
        const val NAME = "web"
    }
}