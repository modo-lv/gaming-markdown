package build.workers

import build.workers.types.Worker
import elements.Eol
import elements.Line
import elements.types.Block
import elements.types.Element

/**
 * Use EOL markers to combine elements into lines.
 */
class LineWorker : Worker() {
    init {
        mustRunAfter(UnwrapContentWorker::class)
    }

    override fun processElement(element: Element): Element = element.apply {
        val lines = mutableListOf<Line>()
        while (subs.any { it is Eol } || (lines.isNotEmpty() && subs.isNotEmpty())) {
            val lineParts = subs.takeWhile { it !is Eol }
            if (lineParts.isNotEmpty()) {
                lines.add(Line().apply { subs = lineParts.toMutableList() })
                subs.removeAll(lineParts)
                if (subs.isNotEmpty())
                    subs.removeAt(0)
            }
        }
        subs.addAll(0, lines)

        // Unwrap lines that contain block elements
        subs = subs.flatMap { element ->
            if (element is Line && element.subs.all { it is Block })
                element.subs
            else
                listOf(element)
        }.toMutableList()
    }
}