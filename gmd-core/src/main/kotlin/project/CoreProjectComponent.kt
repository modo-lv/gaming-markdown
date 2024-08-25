package project

import build.Builder
import build.FromMarkdown
import build.workers.ExplicitIdWorker
import java.nio.file.Path
import kotlin.io.path.PathWalkOption
import kotlin.io.path.pathString
import kotlin.io.path.readText
import kotlin.io.path.walk

class CoreProjectComponent(rootPath: Path) : ProjectComponent<CoreSettings>(
    name = NAME,
    rootPath = rootPath.toAbsolutePath(),
) {
    /**
     * All pages in the project, parsed in alphabetical order (by full path, including subfolders).
     *
     * Runs the parsing on first access.
     */
    val pages: List<Page> by lazy {
        val builder = Builder.create(
            ExplicitIdWorker::class
        )
        val dir = rootPath.resolve(settings.dirs.pages)
        dir.walk(PathWalkOption.INCLUDE_DIRECTORIES)
            .sortedBy { it.pathString }
            .filter { it.pathString.endsWith(".md") }
            .map { file ->
                Page(
                    filePath = file,
                    document = FromMarkdown(file.readText()).toDocument().let(builder::build)
                )
            }
            .toList()
    }

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

    companion object {
        const val NAME = "core"
    }
}