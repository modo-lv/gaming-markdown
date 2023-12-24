package workers

import elements.Content
import main.FromMarkdown
import main.workers.UnwrapContentWorker
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
            .let(UnwrapContentWorker()::processTree)
            .apply {
                findFirstSub<Content>() `should be` null
            }
    }
}