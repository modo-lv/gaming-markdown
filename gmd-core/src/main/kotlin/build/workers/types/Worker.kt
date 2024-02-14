package build.workers.types

import build.BuildContext
import elements.types.Element
import kotlin.reflect.KClass

typealias WorkerClass = KClass<out Worker>
class WorkerCircularDependencyException(message: String) : Exception(message)

/**
 * Common interface for all conversion workers.
 *
 * Workers process [Element] trees created in the first pass and modify them to enable full GMD functionality.
 */
abstract class Worker {
    /**
     * See [BuildContext].
     */
    lateinit var context: BuildContext

    /**
     * Other workers that must be run before this one.
     */
    var runAfter: Set<KClass<out Worker>> = emptySet()
        protected set

    /**
     * Convenience method for setting [runAfter].
     */
    fun mustRunAfter(vararg workers: KClass<out Worker>) {
        runAfter = workers.toSet()
    }

    /**
     * Recursively process an element and all its sub-elements, invoking [processElement] on each one.
     */
    fun processTree(root: Element): Element =
        processElement(root).apply {
            subs = subs.map(::processTree).toMutableList()
        }

    /**
     * Process an element with this worker.
     */
    abstract fun processElement(element: Element): Element
}