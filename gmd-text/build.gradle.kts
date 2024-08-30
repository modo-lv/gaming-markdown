plugins {
    id("gmd-build")
    `java-test-fixtures`
}

dependencies {
    implementation(libs.bundles.full)
    implementation(libs.kte)
    implementation(project(":gmd-core"))

    testImplementation(testFixtures(project(":gmd-core")))
    testImplementation(libs.bundles.core.test)
}