package elements

import elements.types.Inline

class Link(val target: String) : Inline() {
    val isAnchor = target.startsWith("#")
}