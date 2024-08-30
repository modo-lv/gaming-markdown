package utils

import project.CoreProject
import java.nio.file.Path

val CoreProject.pageFilePath: Path get() = rootFilePath.resolve(coreSettings.dirs.pages)
