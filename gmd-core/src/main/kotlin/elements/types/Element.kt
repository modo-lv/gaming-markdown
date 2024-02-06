package elements.types

import elements.Text
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

interface Element {
    /**
     * Element name.
     */
    val typeName: String

    /**
     * Child elements contained by this element.
     */
    var subs: List<Element>

    /**
     * Recursively find the first sub-element of a given type.
     * @param type Type (class) of element to find.
     */
    fun <T> findFirstSub(type: KClass<T>): T? where T : Element {
        @Suppress("UNCHECKED_CAST")
        return subs.firstOrNull { it::class.isSubclassOf(type) } as T?
            ?: subs.map { it.findFirstSub(type) }.firstOrNull()
    }

    /**
     * Extract an element's title (first line in plain text) from it's sub-elements.
     */
    fun titleText(): String {
        fun makeTitle(el: Element): String =
            if (el is Text)
                el.content
            else
                el.subs
                    .takeWhile { it is Inline }
                    .joinToString { makeTitle(it) }

        return findFirstSub<Inline>()
            ?.let(::makeTitle)
            ?: ""
    }

    companion object {
        /**
         * Recursively find the first sub-element of a given type.
         * @param T Type of element to find.
         */
        inline fun <reified T : Element> Element.findFirstSub(): T? =
            findFirstSub(T::class)
    }
}