package elements

import build.Builder
import build.FromMarkdown
import build.workers.HeadingWorker
import elements.types.Element.Companion.firstSub
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

class ExplicitIdTests {
    private val builder = Builder.create(
        HeadingWorker::class
    )

    @Test
    fun `Explicit ID gets set on a heading`() {
        val source = "# Heading {#head}"

        FromMarkdown(source).toDocument()
            .let(builder::build)
            .apply {
                firstSub<Heading>()!!.explicitId `should be equal to` "head"
            }
    }

    @Test
    fun `Explicit ID gets set on a paragraph`() {
        val source = "Arbitrary text. {#text}"

        FromMarkdown(source).toDocument()
            .let(builder::build)
            .apply {
                firstSub<Paragraph>()!!.explicitId `should be equal to` "text"
            }
    }
}