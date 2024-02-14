package build.workers

import build.workers.types.Worker
import elements.Text
import elements.types.Block
import elements.types.Element
import kotlin.text.RegexOption.IGNORE_CASE

/**
 * Parses the `{...}` property line for elements that support it.
 */
class ElementPropertiesWorker : Worker() {
    init {
        mustRunAfter(
            UnwrapContentWorker::class
        )
    }

    override fun processElement(element: Element): Element {
        if (element !is Block)
            return element

        val propString =
            element.subs
                .lastOrNull { it is Text && it.content.startsWith('{') && it.content.endsWith('}') }
                ?.let { (it as Text).content.trim('{', '}') }
                ?: return element

        val id = "^#[a-z_][a-z_0-9]*$".toRegex(IGNORE_CASE)
        propString.split("""\\s+""", ignoreCase = true).forEach { part ->
            when {
                id.matches(part) -> element.explicitId = part.substring(1)
            }
        }
        return element
    }
}