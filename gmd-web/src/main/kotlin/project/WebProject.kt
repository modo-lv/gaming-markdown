package project

import kotlin.io.path.ExperimentalPathApi

class WebProject(
    val core: CoreProjectComponent,
    val web: WebProjectComponent,
) : Project<WebProject> {
    override fun initialize(): WebProject {
        core.initialize()
        web.initialize()
        return this
    }

    @OptIn(ExperimentalPathApi::class)
    override fun build(): WebProject {
        core.build()
        web.build()
        return this
    }
}