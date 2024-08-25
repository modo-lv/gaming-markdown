package project

class TextProject(
    val core: CoreProjectComponent,
    val text: TextProjectComponent,
) : Project<TextProject> {
    override val pages get() = core.pages
    override fun initialize(): TextProject {
        core.initialize()
        text.initialize()
        return this
    }

    override fun build(): TextProject {
        return this
    }
}