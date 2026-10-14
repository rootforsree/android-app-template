plugins {
    alias(libs.plugins.spotless)
}

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**")
        // Line length (120) and indent (4) come from the root .editorconfig;
        // ktlint reads it automatically.
        ktlint(libs.versions.ktlint.get())
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        targetExclude("**/build/**")
        ktlint(libs.versions.ktlint.get())
    }
}
// spotlessCheck is wired into `check` by the Spotless plugin itself.
