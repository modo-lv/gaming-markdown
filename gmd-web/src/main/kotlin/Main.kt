
import project.CliktCommandBase
import project.WebProject

class GmdWeb : CliktCommandBase() {
    override fun run() {
        WebProject(projectPath).initialize()
    }
}

fun main(args: Array<String>) = GmdWeb().main(args)