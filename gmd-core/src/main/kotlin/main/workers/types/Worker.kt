package main.workers.types

import elements.Element
import main.WorkContext

/**
 * Common interface for all second-pass processing workers.
 *
 * Workers process [Element] trees created in the first pass and modify them to enable full GMD functionality.
 */
interface Worker {
    /**
     * See [WorkContext].
     */
    val context: WorkContext

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
    fun processElement(element: Element): Element
}