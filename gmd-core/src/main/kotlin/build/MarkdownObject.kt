package build

import org.intellij.markdown.ast.ASTNode

/**
 * A wrapper that combines a [ASTNode] with a reference to the markdown content.
 * Use when creating elements that need to read their own textual content.
 */
class MarkdownObject(
    node: ASTNode,
    src: String,
) {
    val content = src.substring(node.startOffset..<node.endOffset)
    val type = node.type
}