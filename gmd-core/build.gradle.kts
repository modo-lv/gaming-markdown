plugins {
    id("gmd-build")
}

dependencies {
    implementation(libs.markdown)
    implementation(libs.cli.args)

    testImplementation(libs.bundles.core.test)
}