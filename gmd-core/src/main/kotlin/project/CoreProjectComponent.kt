package project

import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.PathWalkOption
import kotlin.io.path.pathString
import kotlin.io.path.walk

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

    @ExperimentalPathApi
    override fun build() {
        val dir = rootPath.resolve(settings.dirs.pages)
        dir.walk(PathWalkOption.INCLUDE_DIRECTORIES)
            .sortedBy { it.pathString }
            .filter { it.pathString.endsWith(".md") }
            .forEach {
                println(it)
            }
    }

    companion object {
        const val NAME = "core"
    }
}