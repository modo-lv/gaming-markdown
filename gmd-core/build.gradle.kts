plugins {
    id("gmd-build")
    `java-test-fixtures`
}

dependencies {
    implementation(libs.markdown)
    implementation(libs.cli.args)

    testImplementation(libs.bundles.core.test)
}