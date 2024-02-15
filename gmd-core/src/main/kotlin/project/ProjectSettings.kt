package project

/**
 * Project settings as loaded from config file(s).
 */
open class ProjectSettings {
    var name: String = ""

    var labels: Map<String, Label> = emptyMap()


    open class Label {
        lateinit var key: String

        lateinit var name: String

        var icon: String? = null
    }
}