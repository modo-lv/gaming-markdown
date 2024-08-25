package project

import elements.Document
import java.nio.file.Path

/**
 * A wrapper around a parsed GMD file, holding file info as well as the element tree.
 */
class Page(
    val filePath: Path,
    val document: Document,
)