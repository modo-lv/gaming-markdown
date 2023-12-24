plugins {
    id("gmd-build")
}

dependencies {
    implementation(libs.cli.args)
    implementation(libs.kte)
    implementation(project(":gmd-core"))

    testImplementation(libs.bundles.core.test)
}