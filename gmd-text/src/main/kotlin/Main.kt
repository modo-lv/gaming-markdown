import project.CliktCommandBase
import project.TextProject

class GmdText : CliktCommandBase() {
    override fun run() {
        TextProject(rootFilePath = projectPath).run {
            initialize()
            build()
        }
    }
}

fun main(args: Array<String>) = GmdText().main(args)