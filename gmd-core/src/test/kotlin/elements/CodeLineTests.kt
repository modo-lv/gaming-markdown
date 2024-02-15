package elements

import build.Builder
import build.FromMarkdown
import build.workers.CodeLineWorker
import elements.types.Element.Companion.firstSub
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

class CodeLineTests {
    private val builder = Builder.create(
        CodeLineWorker::class
    )

    @Test
    fun `Line of code becomes CodeLine`() {
        val input = "`code`"

        FromMarkdown(input).toDocument()
            .let(builder::build)
            .apply {
                firstSub<CodeLine>()?.content `should be equal to` "code"
            }
    }
}