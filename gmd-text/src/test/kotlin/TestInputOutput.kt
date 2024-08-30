import com.google.common.jimfs.Jimfs
import org.junit.jupiter.api.Test
import project.TextProject
import kotlin.io.path.createDirectories
import kotlin.io.path.readText
import kotlin.io.path.writeText

class TestInputOutput {
    @Test
    fun `Test input output`() {
        val project = TextProject(rootFilePath = Jimfs.newFileSystem().getPath(".")).apply {
            initialize()
        }
        project.pageFilePath.createDirectories()
        project.pathTo(project.pageFilePath.resolve("main.md")).writeText(
            """
# Test
        """.trimIndent()
        )
        project.build()
        project.rootFilePath.resolve("text-out/${project.name}.txt")
            .readText().also(::println)
    }
}