package elements

import elements.types.BlockContainer

/**
 * The root element for a GM page, contains all other elements.
 */
class Document : ElementBase(), BlockContainer {
    /**
     * Path to the file that this document come from, relative to the project's content dir.
     */
    lateinit var filePath: String

    /**
     * Unique document identifier, based on the file path.
     */
    val id: String get() = filePath.removeSuffix(".md")

    /**
     * How deep down this document is in the project file tree (relative to the project's content dir).
     */
    val fileDepth get() = filePath.count { it == '/' }
}