package project

import java.nio.file.Path

class TextProjectComponent(rootPath: Path) : ProjectComponent<TextProjectSettings>(
    name = NAME,
    rootPath = rootPath.toAbsolutePath(),
) {
    override fun initialize() {
        settings = loadSettings(name, defaultConfigPath) ?: TextProjectSettings()
    }

    companion object {
        const val NAME = "text"
    }
}