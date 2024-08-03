package elements

import build.Builder
import build.FromMarkdown
import build.workers.TextCoalesceWorker
import elements.types.Element.Companion.firstSub
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.shouldHaveSize
import org.junit.jupiter.api.Test

class TextCoalesceTests {
    private val builder = Builder.create(
        TextCoalesceWorker::class
    )

    @Test
    fun `Three colons parse as one text element`() {
        val input = """:::""".trimMargin()

        FromMarkdown(input).toDocument()
            .let(builder::build)
            .also { document ->
                document.firstSub<Paragraph>()!!.subs shouldHaveSize 1
                document.firstSub<Text>()!!.content `should be equal to` ":::"
            }
    }
}