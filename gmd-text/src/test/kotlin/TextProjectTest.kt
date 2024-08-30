import com.google.common.jimfs.Jimfs
import org.junit.jupiter.api.BeforeAll
import project.TextProject
import utils.pageFilePath
import kotlin.io.path.createDirectories
import kotlin.io.path.readText
import kotlin.io.path.writeText

abstract class TextProjectTest {
    val project: TextProject by lazy {
        TextProject(rootFilePath = Jimfs.newFileSystem().getPath(".")).apply {
            initialize()
            pageFilePath.createDirectories()
        }
    }

    companion object {
        fun TextProject.writePage(fileName: String = "main.md", content: () -> String) {
            pathTo(pageFilePath.resolve(fileName)).writeText(content())
        }

        fun TextProject.buildOutput(): String =
            build().run {
                pathTo("text-out/${name}.txt").readText()
            }

        @JvmStatic
        @BeforeAll
        fun globalInit(): Unit {

        }
    }
}