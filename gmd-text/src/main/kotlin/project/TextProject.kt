package project

import gg.jte.ContentType
import gg.jte.TemplateEngine
import gg.jte.output.StringOutput
import jte.GmdTemplateResolver

class TextProject(
    val core: CoreProjectComponent,
    val text: TextProjectComponent,
) : Project<TextProject> {
    override val pages get() = core.pages

    override fun initialize(): TextProject {
        core.initialize()
        text.initialize()
        return this.apply {
            Current = this
        }
    }

    override fun build(): TextProject {
        val resolver = GmdTemplateResolver("text-layout/templates")
        val engine = TemplateEngine.create(resolver, ContentType.Plain).apply {
            setTrimControlStructures(true)
        }
        val output = StringOutput()
        engine.render("project.kte", this, output)
        println(output)
        return this
    }

    companion object {
        lateinit var Current: TextProject
    }
}