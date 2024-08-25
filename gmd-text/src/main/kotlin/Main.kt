import project.CliktCommandBase
import project.CoreProjectComponent
import project.TextProject
import project.TextProjectComponent

class GmdText : CliktCommandBase() {
    override fun run() {
        val project = TextProject(
            core = CoreProjectComponent(projectPath),
            text = TextProjectComponent(projectPath),
        )
            .initialize()
            .build()
    }
}

fun main(args: Array<String>) = GmdText().main(args)