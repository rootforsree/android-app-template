plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

// Every plugin a convention plugin may apply by id must be on this classpath.
// Versions come from the version catalog, so pins live in exactly one place.
dependencies {
    implementation(libs.plugins.android.application)
    implementation(libs.plugins.android.library)
    implementation(libs.plugins.android.test)
    implementation(libs.plugins.kotlin.compose)
    implementation(libs.plugins.hilt)
    implementation(libs.plugins.ksp)
    implementation(libs.plugins.protobuf)
    implementation(libs.plugins.roborazzi)
    implementation(libs.plugins.spotless)
    implementation(libs.plugins.detekt)
    implementation(libs.plugins.baselineprofile)
    implementation(libs.plugins.android.junit5)
}
