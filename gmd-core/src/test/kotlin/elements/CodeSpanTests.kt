package elements

import build.Builder
import build.FromMarkdown
import build.workers.CodeSpanWorker
import elements.types.Element.Companion.firstSub
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

class CodeSpanTests {
    private val builder = Builder.create(
        CodeSpanWorker::class
    )

    @Test
    fun `Line of code becomes CodeSpan`() {
        val input = "`code :::`"

        FromMarkdown(input).toDocument()
            .let(builder::build)
            .apply {
                firstSub<CodeSpan>()?.content `should be equal to` "code :::"
            }
    }
}