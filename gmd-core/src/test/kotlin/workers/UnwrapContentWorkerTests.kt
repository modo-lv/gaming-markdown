package workers

import build.Builder
import elements.Content
import build.FromMarkdown
import build.workers.UnwrapContentWorker
import elements.types.Element.Companion.firstSub
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test

class UnwrapContentWorkerTests {
    private val builder = Builder.create(
        UnwrapContentWorker::class
    )

    @Test
    fun `Content elements get removed`() {
        val source =  """
            # Heading
            ## Heading
        """.trimIndent()

        FromMarkdown(source).toDocument()
            .let(builder::build)
            .apply {
                firstSub<Content>() `should be` null
            }
    }
}