package project

class TextProject(
    val core: CoreProjectComponent,
    val text: TextProjectComponent,
) : Project<TextProject> {
    override fun initialize(): TextProject {
        core.initialize()
        text.initialize()
        return this
    }

    override fun build(): TextProject {
        return this
    }
}