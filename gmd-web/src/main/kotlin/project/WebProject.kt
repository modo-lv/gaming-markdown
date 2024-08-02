package project

class WebProject(
    val core: CoreProjectComponent,
    val web: WebProjectComponent,
) : Project<WebProject> {
    override fun initialize(): WebProject {
        core.initialize()
        web.initialize()
        return this
    }

    override fun build(): WebProject {
        core.build()
        web.build()
        return this
    }
}