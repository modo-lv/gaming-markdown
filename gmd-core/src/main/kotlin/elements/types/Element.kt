package elements.types

import elements.Text
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

abstract class Element {
    /**
     * Element name.
     */
    open val typeName by lazy {
        this::class.simpleName!!
    }

    /**
     * Child elements contained by this element.
     */
    var subs: List<Element> = emptyList()

    /**
     * Recursively find the first sub-element of a given type.
     * @param type Type (class) of element to find.
     */
    fun <T> firstSub(type: KClass<T>): T? where T : Element =
        firstSub(type) { true }

    /**
     * Recursively finds the first sub-element of the given type and matching `predicate`.
     */
    fun <T> firstSub(type: KClass<T>, predicate: (T) -> Boolean): T? where T : Element {
        @Suppress("UNCHECKED_CAST")
        return subs.firstOrNull { it::class.isSubclassOf(type) && predicate(it as T) } as T?
            ?: subs.map { it.firstSub(type) }.firstOrNull()
    }

    fun <T> findDirectSubs(type: KClass<T>, predicate: (T) -> Boolean): List<T> where T : Element {
        @Suppress("UNCHECKED_CAST")
        return subs.filter { it::class.isSubclassOf(type) && predicate(it as T) }.map { it as T }
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

        return firstSub<Inline>()
            ?.let(::makeTitle)
            ?: ""
    }

    companion object {
        /**
         * Recursively find the first sub-element of a given type.
         * @param T Type of element to find.
         */
        inline fun <reified T : Element> Element.firstSub(): T? =
            firstSub(T::class)

        /**
         * Recursively find the first sub-element of a given type.
         * @param T Type of element to find.
         */
        inline fun <reified T : Element> Element.firstSub(noinline predicate: (T) -> Boolean): T? =
            firstSub(T::class, predicate)

        inline fun <reified T : Element> Element.findDirectSubs(noinline predicate: (T) -> Boolean): List<T> =
            findDirectSubs(T::class, predicate)
    }
}