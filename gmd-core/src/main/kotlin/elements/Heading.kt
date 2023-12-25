package elements

import elements.types.Block
import build.MarkdownObject

/**
 * A heading.
 */
class Heading(mdo: MarkdownObject): ElementBase(), Block {
    val level = mdo.type.name.last().digitToInt()
}