package elements

import build.FromMarkdown
import build.BuildContext
import build.Builder
import build.workers.HeadingWorker
import build.workers.UnwrapContentWorker
import elements.types.Element.Companion.firstSub
import internal.getLogger
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

class ElementTitleTests {
    private val builder = Builder.create(
        UnwrapContentWorker::class,
        HeadingWorker::class
    )

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