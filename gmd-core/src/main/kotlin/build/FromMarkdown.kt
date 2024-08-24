package build

import com.vladsch.flexmark.ast.SoftLineBreak
import com.vladsch.flexmark.ext.attributes.AttributeNode
import com.vladsch.flexmark.ext.attributes.AttributesExtension
import com.vladsch.flexmark.ext.attributes.AttributesNode
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.ast.Node
import com.vladsch.flexmark.util.data.MutableDataSet
import elements.*
import elements.types.Element

/**
 * Converts Markdown content into our element tree (first pass of the build process).
 * @param src Markdown content to process.
 */
class FromMarkdown(
    private val src: String,
) {
    val opts = MutableDataSet()
        .set(Parser.EXTENSIONS, setOf(AttributesExtension.create()))

    /**
     * Root node of the parsed Markdown tree.
     */
    private val root: MdDocument = Parser.builder(opts).build().parse(src)

    /**
     * Parses the Markdown from `src` into our [Document].
     */
    fun toDocument(): Document = nodeToElement(root) as Document

    /**
     * Recursively converts [] and all its children to our [Element]s.
     */
    fun nodeToElement(node: Node): Element? {
        val element = when (node) {
            is MdCode -> CodeSpan()
            is MdDocument -> Document()
            is MdHeading -> Heading(node)
            is MdParagraph -> Paragraph()
            is MdText -> Text("${node.chars}")

            is AttributesNode -> null
            is SoftLineBreak -> null

            else -> Placeholder(node)
        }

        element?.also { el ->
            el.attributes = node.lastChild.takeIf { it is AttributesNode }?.children?.map { it as AttributeNode }
            el.subs = node.children.mapNotNull(::nodeToElement).toMutableList()
        }

        return element
    }
}