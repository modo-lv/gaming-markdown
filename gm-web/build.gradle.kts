plugins {
    id("gm-build")
}

dependencies {
    implementation(libs.cli.args)
    implementation(project(":gm-core"))

    testImplementation(libs.bundles.core.test)
}