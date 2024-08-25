package jte

import gg.jte.CodeResolver
import gg.jte.resolve.ResourceCodeResolver

class GmdTemplateResolver(root: String) : CodeResolver {
    val resourceResolver = ResourceCodeResolver(root)

    override fun resolve(name: String): String {
        val result = resourceResolver.resolve(name)
            // Ensure that included elements don't add unintended newlines
            .replace("""(?:\r\n?|\n)[ \t]+(@template.+)\s*""".toRegex(), "$1")
            .replace("""(@end\w+)\s+""".toRegex(), "$1")
        return result
    }

    override fun getLastModified(name: String): Long = resourceResolver.getLastModified(name)
}