package elements

import elements.types.Inline
import org.intellij.markdown.IElementType

/**
 * Fallback element for unknown [IElementType]s.
 * @param mdType Type name of the Markdown node.
 */
class Placeholder(
    mdType: IElementType
) : Element(), Inline {
    override val typeName: String = mdType.name
}