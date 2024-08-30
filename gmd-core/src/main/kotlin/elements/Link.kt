package elements

import elements.types.Inline

class Link(val target: String) : Inline() {
    /**
     * Indicates that the link points to an ID in the project.
     */
    val isInternal = target.startsWith("#")

    /**
     * Indicates that the link points to a nonexistent resource.
     */
    var isBroken = target == ""
}