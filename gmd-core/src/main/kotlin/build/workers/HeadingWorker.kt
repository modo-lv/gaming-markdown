package build.workers

import build.workers.types.Worker
import elements.Heading
import elements.Text
import elements.types.Element

/**
 * Processes [Heading] elements.
 */
class HeadingWorker : Worker() {
    init {
        mustRunAfter(
            UnwrapContentWorker::class
        )
    }

    override fun processElement(element: Element): Element {
        if (element !is Heading) return element

        // intellij-markdown parses leading/trailing whitespace as separate elements,
        // allowing easy removal
        element.subs = element.subs.filterNot {
            it is Text && it.content.isBlank()
        }

        return element
    }
}