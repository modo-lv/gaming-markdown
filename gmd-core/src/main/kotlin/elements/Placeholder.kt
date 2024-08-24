package elements

import elements.types.Inline

/**
 * Fallback element for unknown [IElementType]s.
 * @param mdNode Type name of the Markdown node.
 */
class Placeholder(
    mdNode: Any
) : Inline() {
    override val typeName: String = mdNode::class.simpleName!!
}