package project

import kotlin.io.path.ExperimentalPathApi

class TextProject(
    val core: CoreProjectComponent,
    val text: TextProjectComponent,
) : Project<TextProject> {
    override fun initialize(): TextProject {
        core.initialize()
        text.initialize()
        return this
    }

    @OptIn(ExperimentalPathApi::class)
    override fun build(): TextProject {
        core.build()
        text.build()
        return this
    }
}