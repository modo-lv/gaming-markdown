plugins {
    `java-gradle-plugin`
    kotlin("jvm") version (libs.versions.kotlin.get())
}

dependencies {
    // Plugins as regular dependencies allow access to their classes in our own plugins.
    implementation(libs.gradle.kotlin)
    implementation(libs.kt.serial)
}

gradlePlugin {
    plugins {
        create("gmd-build") {
            id = "gmd-build"
            implementationClass = "GmdBuild"
        }
    }
}