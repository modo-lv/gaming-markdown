package elements

import build.MdHeading
import elements.types.Block

/**
 * A heading.
 */
class Heading(heading: MdHeading): Block() {
    val level = heading.level
}