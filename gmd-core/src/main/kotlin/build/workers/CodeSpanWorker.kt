package build.workers

import build.workers.types.Worker
import elements.CodeSpan
import elements.Text
import elements.types.Element
import elements.types.Element.Companion.firstSub

/**
 * Flattens code span's text sub-element into its content.
 */
class CodeSpanWorker : Worker() {
    init {
        mustRunAfter(
            TextCoalesceWorker::class
        )
    }

    override fun processElement(element: Element): Element {
        if (element !is CodeSpan) return element

        return element.apply {
            content = this.firstSub<Text>()!!.content
            subs.clear()
        }
    }
}