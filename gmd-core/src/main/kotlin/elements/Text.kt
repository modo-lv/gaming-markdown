package elements

import elements.types.Inline
import build.MarkdownObject

/**
 * Basic plain text.
 */
class Text(mdo: MarkdownObject): Inline() {
    val content = mdo.content
}