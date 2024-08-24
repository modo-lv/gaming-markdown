package build.workers

import build.workers.types.Worker
import elements.types.Element

/**
 * Parses the `{...}` property line for elements that support it.
 */
class ExplicitIdWorker : Worker() {
    override fun processElement(element: Element): Element {
        if (element.attributes == null)
            return element

        element.attributes?.forEach { attribute ->
            if (attribute.name.equals("#")) {
                element.explicitId = "${attribute.value}"
            }
        }

        return element
    }
}