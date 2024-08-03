package build

import elements.*
import elements.types.Element
import org.intellij.markdown.MarkdownElementTypes.ATX_1
import org.intellij.markdown.MarkdownElementTypes.ATX_2
import org.intellij.markdown.MarkdownElementTypes.ATX_3
import org.intellij.markdown.MarkdownElementTypes.ATX_4
import org.intellij.markdown.MarkdownElementTypes.ATX_5
import org.intellij.markdown.MarkdownElementTypes.ATX_6
import org.intellij.markdown.MarkdownElementTypes.CODE_SPAN
import org.intellij.markdown.MarkdownElementTypes.EMPH
import org.intellij.markdown.MarkdownElementTypes.MARKDOWN_FILE
import org.intellij.markdown.MarkdownElementTypes.PARAGRAPH
import org.intellij.markdown.MarkdownElementTypes.STRONG
import org.intellij.markdown.MarkdownTokenTypes.Companion.ATX_CONTENT
import org.intellij.markdown.MarkdownTokenTypes.Companion.ATX_HEADER
import org.intellij.markdown.MarkdownTokenTypes.Companion.TEXT
import org.intellij.markdown.MarkdownTokenTypes.Companion.WHITE_SPACE
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser

/**
 * Converts Markdown content into our element tree (first pass of the build process).
 * @param src Markdown content to process.
 */
class FromMarkdown(
    private val src: String,
) {
    /**
     * Root node of the parsed Markdown tree.
     */
    private val root = MarkdownParser(CommonMarkFlavourDescriptor()).buildMarkdownTreeFromString(src)

    /**
     * Parses the Markdown from `src` into our [Document].
     */
    fun toDocument(): Document = nodeToElement(root) as Document

    /**
     * Recursively converts [ASTNode] and all its children to our [Element]s.
     */
    fun nodeToElement(node: ASTNode): Element? {
        val element =
            MarkdownObject(node, src).let {
                when (it.type) {
                    MARKDOWN_FILE -> Document()

                    ATX_1, ATX_2, ATX_3, ATX_4, ATX_5, ATX_6 -> Heading(it)
                    CODE_SPAN -> CodeLine()
                    EMPH, STRONG -> Emphasis(it)
                    PARAGRAPH -> Paragraph()
                    TEXT, WHITE_SPACE -> Text(it)

                    ATX_CONTENT -> Content()
                    ATX_HEADER -> null

                    else -> Placeholder(node.type)
                }
            }

        element?.subs = node.children.mapNotNull(::nodeToElement).toMutableList()

        return element
    }
}