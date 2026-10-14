import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension

plugins {
    // The Compose compiler plugin, versioned with Kotlin (the catalog pins
    // kotlin = 2.4.20 for both). Strong skipping is automatic on Kotlin 2.4+;
    // do NOT add an enableStrongSkippingMode flag, it is a build error.
    alias(libs.plugins.kotlin.compose)
}

// Order-independent: works whether the Android convention ran before or after.
pluginManager.withPlugin("com.android.application") {
    extensions.configure<ApplicationExtension> {
        buildFeatures {
            compose = true
        }
    }
}

pluginManager.withPlugin("com.android.library") {
    extensions.configure<LibraryExtension> {
        buildFeatures {
            compose = true
        }
    }
}
