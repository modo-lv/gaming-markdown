
import project.CoreClicktCommand
import project.CoreProject
import project.TextProject

class GmdText : CoreClicktCommand() {
    override fun run() {
        val core = CoreProject(projectPath)
        val project = TextProject(core).initialize()
    }
}

fun main(args: Array<String>) = GmdText().main(args)