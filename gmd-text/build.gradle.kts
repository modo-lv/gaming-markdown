plugins {
    id("gmd-build")
}

dependencies {
    implementation(libs.bundles.full)
    implementation(libs.kte)
    implementation(project(":gmd-core"))

    testImplementation(libs.bundles.core.test)
}