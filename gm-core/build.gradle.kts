plugins {
    id("gm-build")
}

dependencies {
    implementation(libs.markdown)

    testImplementation(libs.bundles.core.test)
}