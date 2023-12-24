plugins {
    id("gmd-build")
}

dependencies {
    implementation(libs.cli.args)
    implementation(project(":gmd-core"))

    testImplementation(libs.bundles.core.test)
}