package build.workers

import elements.Content
import build.workers.types.Worker
import elements.types.Element

/**
 * "Unwraps" [Content] element by replacing it with its sub-elements.
 */
class UnwrapContentWorker : Worker() {
    override fun processElement(element: Element): Element {
        element.apply {
            while (subs.any { it is Content }) {
                subs = subs.flatMap { el ->
                    if (el is Content)
                        el.subs
                    else
                        listOf(el)
                }.toMutableList()
            }
        }

        return element
    }
}