import project.CliktCommandBase
import project.CoreProjectComponent
import project.WebProject
import project.WebProjectComponent

class GmdWeb : CliktCommandBase() {
    override fun run() {
        WebProject(
            core = CoreProjectComponent(projectPath),
            web = WebProjectComponent(projectPath),
        ).initialize()
    }
}

fun main(args: Array<String>) = GmdWeb().main(args)