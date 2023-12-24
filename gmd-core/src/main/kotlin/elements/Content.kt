package elements

import org.intellij.markdown.MarkdownTokenTypes.Companion.ATX_CONTENT

/**
 * Pseudo-element wrapper for any kind of text content.
 * Only exists during the first pass to match [ATX_CONTENT],
 * should be removed by the appropriate worker during second pass.
 */
class Content : Element()