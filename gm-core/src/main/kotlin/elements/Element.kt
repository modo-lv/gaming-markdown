package elements

import kotlin.reflect.KClass

/**
 * Base class for all GM elements.
 */
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
    var subs: List<Element> = listOf()


    /**
     * Recursively find the first sub-element of a given type.
     * @param T Type of element to find.
     */
    inline fun <reified T : Element> findFirstSub(): T? =
        findFirstSub(T::class)

    /**
     * Recursively find the first sub-element of a given type.
     * @param type Type (class) of element to find.
     */
    fun <T> findFirstSub(type: KClass<T>): T? where T : Element {
        @Suppress("UNCHECKED_CAST")
        return subs.firstOrNull { it::class == type } as T?
            ?: subs.map { it.findFirstSub(type) }.firstOrNull()
    }
}