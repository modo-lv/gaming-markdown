package project

import java.nio.file.Path

class WebProject(rootFilePath: Path) : CoreProject(rootFilePath = rootFilePath) {
    lateinit var webSettings: WebProjectSettings

    override fun initialize() {
        webSettings = loadSettings("web", configFilePath) ?: WebProjectSettings()
    }
}