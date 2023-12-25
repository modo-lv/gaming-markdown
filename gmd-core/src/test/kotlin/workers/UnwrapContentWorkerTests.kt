package workers

import elements.Content
import build.FromMarkdown
import build.WorkContext
import build.workers.UnwrapContentWorker
import elements.types.Element.Companion.findFirstSub
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test

class UnwrapContentWorkerTests {
    @Test
    fun `Content elements get removed`() {
        val source =  """
            # Heading
            ## Heading
        """.trimIndent()

        FromMarkdown(source).toDocument()
            .let(UnwrapContentWorker(WorkContext())::processTree)
            .apply {
                findFirstSub<Content>() `should be` null
            }
    }
}