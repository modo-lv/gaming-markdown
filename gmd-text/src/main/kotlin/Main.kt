import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.path
import project.CoreProject
import project.TextProject

class GmdText : CliktCommand() {
    val projectPath by argument("project-path", "Location of the project files for this build.").path(
        mustExist = true,
        canBeFile = false,
        mustBeWritable = true,
    )

    override fun run() {
        val core = CoreProject(projectPath)
        val project = TextProject(core).initialize()
        println(project)
    }
}

fun main(args: Array<String>) = GmdText().main(args)