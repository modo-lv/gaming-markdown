package elements

import build.MarkdownObject
import elements.types.Inline

/**
 * Basic plain text.
 */
class Text(val content: String) : Inline() {
    constructor(mdo: MarkdownObject) : this(mdo.content)
}