package build.workers

import build.workers.types.Worker
import elements.Text
import elements.types.Element

/**
 * Merges multiple [Text] elements in a row into a single [Text] element.
 */
class TextCoalesceWorker : Worker() {
    init {
        mustRunAfter(
            LineWorker::class
        )
    }

    override fun processElement(element: Element): Element =
        element.apply {
            var last = subs.lastIndex
            var a = 0
            while (a < last) {
                val current = subs[a] as? Text
                val next = subs[a + 1] as? Text
                if (current != null && next != null) {
                    subs[a] = Text(current.content + next.content)
                    subs.remove(next)
                    last -= 1
                }
                else {
                    a += 1
                }
            }
        }
}