package elements

import elements.types.Inline
import main.MarkdownObject

/**
 * Basic plain text.
 */
class Text(mdo: MarkdownObject): Element(), Inline {
    val content = mdo.content
}