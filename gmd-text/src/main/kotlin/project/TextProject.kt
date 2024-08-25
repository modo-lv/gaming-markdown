package project

import gg.jte.ContentType
import gg.jte.TemplateEngine
import gg.jte.output.StringOutput
import io.github.oshai.kotlinlogging.KotlinLogging
import jte.GmdTemplateResolver
import java.nio.file.Path
import kotlin.io.path.absolute
import kotlin.io.path.writeText

private val logger = KotlinLogging.logger {  }

class TextProject(
    val core: CoreProjectComponent,
    val text: TextProjectComponent,
) : Project<TextProject> {
    override val pages get() = core.pages

    lateinit var outputFile: Path

    override fun initialize(): TextProject {
        core.initialize()
        text.initialize()
        return this.apply {
            outputFile = text.rootPath
                .resolve(text.settings.outputDir)
                .resolve(text.settings.outputFile.replace("{name}", core.name))
            Current = this
        }
    }

    override fun build(): TextProject {
        val resolver = GmdTemplateResolver("text-layout/templates")
        val engine = TemplateEngine.create(resolver, ContentType.Plain).apply {
            setTrimControlStructures(true)
        }
        val output = StringOutput()
        logger.info { "Generating text output and saving to [${outputFile.absolute()}]..." }
        engine.render("project.kte", this, output)
        outputFile.absolute().writeText(output.toString())
        return this
    }

    companion object {
        lateinit var Current: TextProject
    }
}