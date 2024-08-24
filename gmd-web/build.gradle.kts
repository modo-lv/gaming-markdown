plugins {
    id("gmd-build")
}

dependencies {
    implementation(libs.cli.args)
    implementation(libs.kte)
    implementation(project(":gmd-core"))
    implementation("de.inetsoftware:jlessc:1.12")

    testImplementation(libs.bundles.core.test)
}