rootProject.name = "gaming-markdown"

include(
    "gm-core",

    "gm-web",
)

pluginManagement {
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("build-logic/gradle/libs.versions.toml"))
        }
    }
}