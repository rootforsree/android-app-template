plugins {
    // Versionless ids: the plugin markers are on build-logic's classpath
    // (see build-logic/build.gradle.kts), so the ids resolve from there.
    // alias(libs.plugins.*) does NOT work here: the version catalog is not
    // in scope inside a precompiled script plugin's plugins {} block.
    // KSP for all annotation processors. KAPT is dead, never use it.
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

dependencies {
    add("implementation", libs.findLibrary("hilt-android").get())
    add("ksp", libs.findLibrary("hilt-compiler").get())
}
