import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.path
import project.CoreProject
import project.WebProject

class GmdWeb : CliktCommand() {
    val projectPath by argument("project-path", "Location of the project files for this build.").path(
        mustExist = true,
        canBeFile = false,
        mustBeWritable = true,
    )

    override fun run() {
        val core = CoreProject(projectPath)
        val project = WebProject(core).initialize()
        println(project)
    }
}

fun main(args: Array<String>) = GmdWeb().main(args)