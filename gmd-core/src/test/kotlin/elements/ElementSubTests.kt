package elements

import build.Builder
import build.FromMarkdown
import elements.types.Element.Companion.firstSub
import org.amshove.kluent.`should be instance of`
import org.junit.jupiter.api.Test

class ElementSubTests {
    private val builder = Builder.create()

    @Test
    fun `findFirstSub finds heading`() {
        val source = "# Heading"

        FromMarkdown(source).toDocument()
            .let(builder::build)
            .apply {
                firstSub<Heading>() `should be instance of` Heading::class
            }
    }
}