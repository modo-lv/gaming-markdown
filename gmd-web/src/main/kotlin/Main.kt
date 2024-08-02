
import project.CoreClicktCommand
import project.CoreProject
import project.WebProject

class GmdWeb : CoreClicktCommand() {
    override fun run() {
        val core = CoreProject(projectPath)
        val project = WebProject(core).initialize()
    }
}

fun main(args: Array<String>) = GmdWeb().main(args)