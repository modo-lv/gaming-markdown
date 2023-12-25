package elements.types

import kotlin.reflect.KClass

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
        return subs.firstOrNull { it::class == type } as T?
            ?: subs.map {  it.findFirstSub(type) }.firstOrNull()
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