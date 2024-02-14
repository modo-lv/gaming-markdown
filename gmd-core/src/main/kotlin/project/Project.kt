package project

import java.nio.file.Path
import kotlin.io.path.name

/**
 * Base class for GMD projects.
 */
open class Project(rootPath: Path) {
    val rootPath: Path = rootPath.toAbsolutePath()

    /**
     * project.Project name, for display and logging purposes.
     */
    var name: String = rootPath.name

    /**
     * Path to source Markdown pages within the project.
     */
    var pageDir: Path = Path.of("pages")


    override fun toString(): String =
        "${this::class.simpleName}(" +
                "${this::rootPath.name}=${this.rootPath}, " +
                "${this::name.name}=${this.name}, " +
                "${this::pageDir.name}=${this.pageDir}" +
                ")"
}