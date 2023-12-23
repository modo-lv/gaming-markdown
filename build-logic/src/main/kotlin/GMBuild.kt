import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class GMBuild : Plugin<Project> {
    override fun apply(project: Project) {
        project.group = "lv.modo.gaming.markdown"
        project.rootProject.repositories.mavenCentral()
        // Kotlin compilation
        project.pluginManager.apply(KotlinPluginWrapper::class.java)
        // Consistent JVM targeting
        project.tasks.withType(KotlinCompile::class.java) { it.kotlinOptions.jvmTarget = project.jvmTarget }
        project.tasks.withType(JavaCompile::class.java) { it.targetCompatibility = project.jvmTarget }
        // Testing
        project.dependencies.addProvider("testImplementation", project.testFramework)
        project.dependencies.add("testRuntimeOnly", "org.junit.platform:junit-platform-launcher")
        project.tasks.withType(Test::class.java) {
            it.useJUnitPlatform()
        }
    }

    /**
     * Use the JVM target specified in `libs.versions.toml`.
     */
    private val Project.jvmTarget
        get() = this
            .versionCatalog
            .findVersion("jvm")
            .get()
            .displayName

    private val Project.testFramework
        get() = this
            .versionCatalog
            .findLibrary("junit")
            .get()

    private val Project.versionCatalog
        get() = this.rootProject.extensions
            .getByType(VersionCatalogsExtension::class.java)
            .named("libs")
}