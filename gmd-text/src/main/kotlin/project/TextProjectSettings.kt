package project

import kotlinx.serialization.Serializable

@Serializable
class TextProjectSettings(
    val pageWidth: Int = 30
)