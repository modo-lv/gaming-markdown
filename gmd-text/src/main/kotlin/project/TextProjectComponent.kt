package project

import java.nio.file.Path

class TextProjectComponent(rootPath: Path) : ProjectComponent<TextProjectSettings>(
    componentType = NAME,
    rootPath = rootPath.toAbsolutePath(),
) {
    override fun initialize() {
        settings = loadSettings(componentType, defaultConfigPath) ?: TextProjectSettings()
    }

    companion object {
        const val NAME = "text"
    }
}