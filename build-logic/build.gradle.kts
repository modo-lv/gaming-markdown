plugins {
    `java-gradle-plugin`
    kotlin("jvm") version (libs.versions.kotlin.get())
}

dependencies {
    // Plugins as regular dependencies allow access to their classes in our own plugins.
    implementation(libs.gradle.kotlin)
}

gradlePlugin {
    plugins {
        create("gm-build") {
            id = "gm-build"
            implementationClass = "GMBuild"
        }
    }
}