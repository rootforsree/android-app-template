plugins {
    alias(libs.plugins.hilt)
    // KSP for all annotation processors. KAPT is dead, never use it.
    alias(libs.plugins.ksp)
}

dependencies {
    add("implementation", libs.findLibrary("hilt-android").get())
    add("ksp", libs.findLibrary("hilt-compiler").get())
}
