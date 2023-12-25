package elements

import elements.types.Inline
import build.MarkdownObject

/**
 * Basic plain text.
 */
class Text(mdo: MarkdownObject): ElementBase(), Inline {
    val content = mdo.content
}