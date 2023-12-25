package elements

import elements.types.Element

/**
 * Base class for all GM elements.
 */
abstract class ElementBase : Element {
    override val typeName by lazy {
        this::class.simpleName!!
    }

    override var subs: List<Element> = listOf()
}