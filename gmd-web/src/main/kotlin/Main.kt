import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.path
import project.Project

class GmdWeb : CliktCommand() {
    val projectPath by argument("project-path", "Location of the project files for this build.").path(
        mustExist = true,
        canBeFile = false,
        mustBeWritable = true,
    )

    override fun run() {
        println(Project(projectPath))
    }
}

fun main(args: Array<String>) = GmdWeb().main(args)