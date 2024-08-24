package elements

import build.Builder
import build.FromMarkdown
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should match all with`
import org.junit.jupiter.api.Test

class ParagraphTests {
    private val builder = Builder.create()

    @Test
    fun `Single paragraph with multiple lines`() {
        val input = """First line
            | Second line
            |  Third line
        """.trimMargin()

        FromMarkdown(input).toDocument()
            .let(builder::build)
            .subs.single { it is Paragraph }.also { paragraph ->
                paragraph.subs.size `should be equal to` 3
                paragraph.subs `should match all with` { it is Text }
            }
    }
}