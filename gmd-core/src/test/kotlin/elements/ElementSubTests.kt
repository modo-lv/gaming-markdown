package elements

import build.FromMarkdown
import build.WorkContext
import build.workers.UnwrapContentWorker
import elements.types.Element.Companion.findFirstSub
import org.amshove.kluent.`should be instance of`
import org.junit.jupiter.api.Test

class ElementSubTests {
    @Test
    fun `findFirstSub finds heading`() {
        val source = "# Heading"

        FromMarkdown(source).toDocument()
            .let(UnwrapContentWorker(WorkContext())::processTree)
            .apply {
                findFirstSub<Heading>() `should be instance of` Heading::class
            }
    }
}