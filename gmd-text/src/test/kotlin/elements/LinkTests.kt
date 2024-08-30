package elements

import TextProjectTest
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

class LinkTests : TextProjectTest() {
    @Test
    fun `Broken link renders correctly`() {
        project.writePage { "[X]" }
        project.buildOutput() `should be equal to` "[X]"
    }
}