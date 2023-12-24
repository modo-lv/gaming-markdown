plugins {
    id("gmd-build")
}

dependencies {
    implementation(libs.markdown)

    testImplementation(libs.bundles.core.test)
}