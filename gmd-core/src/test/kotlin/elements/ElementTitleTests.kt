package elements

import build.FromMarkdown
import elements.types.Element.Companion.findFirstSub
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test

class ElementTitleTests {
    @Test
    fun `Heading title gets extracted correctly`() {
        val source = "# Heading"

        FromMarkdown(source).toDocument()
            .apply {
                findFirstSub<Heading>()!!.titleText() `should be` "Heading"
            }
    }
}