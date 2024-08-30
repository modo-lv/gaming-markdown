package elements

import TextProjectTest
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

class HeadingTests : TextProjectTest() {
    val h1Line = "#".repeat(project.textSettings.pageWidth)
    val h2Line = "=".repeat(project.textSettings.pageWidth)

    @Test
    fun `H1 outputs correctly`() {
        project.writePage { "# Level 1" }
        project.buildOutput() `should be equal to` """
            ${h1Line}
            Level 1
            ${h1Line}
            """.trimIndent()
    }

    @Test
    fun `H2 leaves space after itself`() {
        project.writePage {
            """
            ## Level 2
            X
            """.trimIndent()
        }
        project.buildOutput() `should be equal to` """
            ${h2Line}
            Level 2
            ${h2Line}
            
            X
            """.trimIndent()
    }
}