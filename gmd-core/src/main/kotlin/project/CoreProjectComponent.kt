package project

import java.nio.file.Path

class CoreProjectComponent(rootPath: Path) : ProjectComponent<CoreSettings>(
    name = NAME,
    rootPath = rootPath.toAbsolutePath(),
) {
    /**
     * Initialize the project: load settings, perform basic sanity checks etc.
     */
    override fun initialize() {
        settings = loadSettings<CoreSettings>(name, defaultConfigPath)
            ?.takeIf { it.name.isNotBlank() }
            ?.also { settings ->
                settings.labels.forEach {
                    it.value.key = it.key
                }
            }
            ?: throw Exception("Project must have a configuration file with at least [core.name] set.")
    }

    override fun build() {
        TODO("Not yet implemented")
    }

    companion object {
        const val NAME = "core"
    }
}