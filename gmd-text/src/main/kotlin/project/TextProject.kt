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

class TextProject(rootFilePath: Path) : CoreProject(rootFilePath = rootFilePath) {
    lateinit var outputFile: Path

    lateinit var textSettings: TextProjectSettings

    override fun initialize() {
        super.initialize()
        textSettings = loadSettings("text", configFilePath) ?: TextProjectSettings()
        this.apply {
            outputFile = rootFilePath
                .resolve(textSettings.outputDir)
                .resolve(textSettings.outputFile.replace("{name}", projectName))
            Current = this
        }
    }

    fun build(): TextProject {
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