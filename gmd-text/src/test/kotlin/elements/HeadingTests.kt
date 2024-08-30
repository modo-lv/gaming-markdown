package elements

import TextProjectTest
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

class HeadingTests : TextProjectTest() {
    @Test
    fun `H1 outputs correctly`() {
        project.writePage {
            """
            # Level 1
            """.trimIndent()
        }

        project.buildOutput() `should be equal to` """
            
            # LEVEL 1
            
            """.trimIndent()
    }
}