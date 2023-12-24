package elements

import elements.types.Block
import main.MarkdownObject

class Heading(mdo: MarkdownObject): Element(), Block {
    val level = mdo.type.name.last().digitToInt()
}