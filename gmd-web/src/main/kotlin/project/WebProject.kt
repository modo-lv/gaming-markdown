package project

class WebProject(
    val core: CoreProjectComponent,
    val web: WebProjectComponent,
) : Project<WebProject> {
    override val pages get() = core.pages

    override fun initialize(): WebProject {
        core.initialize()
        web.initialize()
        return this
    }

    override fun build(): WebProject {
        return this
    }
}