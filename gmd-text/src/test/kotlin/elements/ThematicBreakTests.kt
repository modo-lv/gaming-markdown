package elements

import TextProjectTest
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should match`
import org.junit.jupiter.api.Test

class ThematicBreakTests : TextProjectTest() {
    val hr = "-".repeat(project.textSettings.pageWidth)

    @Test
    fun `Renders correctly`() {
        project.writePage { "---" }
        project.buildOutput() `should be equal to` hr
    }

    @Test
    fun `Empty line between break and next element`() {
        project.writePage { "---\nX"}
        project.buildOutput() `should match` """
            ${hr}
            
            X
        """.trimIndent()
    }
}