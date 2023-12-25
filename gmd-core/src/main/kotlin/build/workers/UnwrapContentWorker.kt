package build.workers

import elements.Content
import elements.Element
import build.WorkContext
import build.workers.types.Worker

/**
 * "Unwraps" [Content] element by replacing it with its sub-elements.
 */
class UnwrapContentWorker(override val context: WorkContext) : Worker {
    override fun processElement(element: Element): Element {
        element.apply {
            while (subs.any { it is Content }) {
                subs = subs.flatMap { el ->
                    if (el is Content)
                        el.subs
                    else
                        listOf(el)
                }
            }
        }

        return element
    }
}