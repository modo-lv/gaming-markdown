package build.workers

import build.workers.types.Worker
import elements.CodeLine
import elements.Text
import elements.types.Element
import elements.types.Element.Companion.firstSub

class CodeLineWorker : Worker() {
    init {
        mustRunAfter(
            UnwrapContentWorker::class
        )
    }

    override fun processElement(element: Element): Element {
        if (element !is CodeLine) return element

        return element.apply {
            content = this.firstSub<Text>()!!.content
            subs = emptyList()
        }
    }
}