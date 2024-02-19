package project

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Project settings as loaded from config file(s).
 */
@Serializable
data class CoreProjectSettings(
    val name: String,
    val dirs: CoreDirs = CoreDirs(),
    val labels: Map<String, CoreLabel> = emptyMap(),
) {

    @Serializable
    data class CoreDirs(
        val pages: String = "pages"
    )

    @Serializable
    data class CoreLabel(
        val name: String,
        val icon: String? = null,
    ) {
        @Transient
        lateinit var key: String
    }
}