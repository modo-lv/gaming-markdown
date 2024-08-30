package project

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Project settings as loaded from config file(s).
 */
@Serializable
open class CoreProjectSettings {
    var name: String? = null
    var title: String? = null
    var dirs: CoreDirs = CoreDirs()
    var labels: Map<String, CoreLabel> = emptyMap()

    @Serializable
    class CoreDirs(
        var pages: String = "pages"
    )

    @Serializable
    class CoreLabel(val name: String) {
        val icon: String? = null

        @Transient
        lateinit var key: String
    }
}