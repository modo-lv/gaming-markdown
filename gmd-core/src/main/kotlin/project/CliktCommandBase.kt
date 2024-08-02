package project

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.path

abstract class CliktCommandBase : CliktCommand() {
    val projectPath by argument("project-path", "Location of the project files for this build.").path(
        mustExist = true,
        canBeFile = false,
        mustBeWritable = true,
    )
}