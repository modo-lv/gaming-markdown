package elements

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

class DocumentTests {
    @Test
    fun `ID is set correctly based on its file path`() {
        Document().apply { filePath = "chapter1/page1.md" }
            .id `should be equal to` "chapter1/page1"
    }

    @Test
    fun `Document depth is correctly based on its file path`() {
        Document().apply { filePath = "part1/chapter1/page1.md" }
            .fileDepth `should be equal to` 2
    }
}