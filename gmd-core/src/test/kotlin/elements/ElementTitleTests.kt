package elements

import build.Builder
import build.FromMarkdown
import elements.types.Element.Companion.firstSub
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

class ElementTitleTests {
    private val builder = Builder.create()

    @Test
    fun `Heading title gets extracted correctly`() {
        val source = "# Heading"

        FromMarkdown(source).toDocument()
            .let(builder::build)
            .apply {
                firstSub<Heading>()!!.titleText() `should be equal to` "Heading"
            }
    }
}