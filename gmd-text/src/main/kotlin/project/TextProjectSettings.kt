package project

import kotlinx.serialization.Serializable

@Serializable
class TextProjectSettings(
    val outputDir: String = "text-out",
    val outputFile: String = "{name}.txt",
    val pageWidth: Int = 80,
)