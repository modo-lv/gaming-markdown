package project

import build.Builder
import build.FromMarkdown
import build.workers.ExplicitIdWorker
import java.nio.file.Path
import kotlin.io.path.*

class CoreProjectComponent(rootPath: Path) : ProjectComponent<CoreProjectSettings>(
    componentType = COMPONENT_TYPE,
    rootPath = rootPath.toAbsolutePath(),
) {
    /**
     * Short name of the project.
     *
     * Can be set explicitly in config, otherwise inferred from the name of the directory the project is in.
     */
    var name: String = rootPath.fileName.nameWithoutExtension

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
        settings = loadSettings<CoreProjectSettings>(componentType, defaultConfigPath)
            ?.also { settings ->
                settings.name?.also { this.name = it }
                settings.labels.forEach {
                    it.value.key = it.key
                }
            }
            ?: CoreProjectSettings()
    }

    companion object {
        const val COMPONENT_TYPE = "core"
    }
}