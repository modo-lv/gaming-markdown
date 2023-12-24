package main

import elements.*
import org.intellij.markdown.MarkdownElementTypes.ATX_1
import org.intellij.markdown.MarkdownElementTypes.MARKDOWN_FILE
import org.intellij.markdown.MarkdownTokenTypes.Companion.ATX_CONTENT
import org.intellij.markdown.MarkdownTokenTypes.Companion.ATX_HEADER
import org.intellij.markdown.MarkdownTokenTypes.Companion.TEXT
import org.intellij.markdown.MarkdownTokenTypes.Companion.WHITE_SPACE
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser

/**
 * Converts Markdown content into ours.
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
     * Parses the Markdown form `src` into our [Document].
     */
    fun toDocument(): Document = nodeToElement(root) as Document

    /**
     * Recursively converts [ASTNode] and all its children to our [Element]s.
     */
    fun nodeToElement(node: ASTNode): Element? {
        val element =
            MarkdownObject(node, src).let {
                when (it.type) {
                    ATX_1 -> Heading(it)
                    ATX_CONTENT -> Content()
                    ATX_HEADER -> null
                    MARKDOWN_FILE -> Document()
                    TEXT -> Text(it)
                    WHITE_SPACE -> Text(it)
                    else -> Placeholder(node.type)
                }
            }

        element?.subs = node.children.mapNotNull(::nodeToElement)

        return element
    }
}