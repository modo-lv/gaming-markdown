package elements

import build.MarkdownObject
import elements.types.Inline
import org.intellij.markdown.MarkdownElementTypes.STRONG

class Emphasis(mdo: MarkdownObject): Inline() {
    val isStrong: Boolean =
        mdo.type == STRONG
}