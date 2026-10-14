import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

plugins {
    // Versionless: the plugin marker is on build-logic's classpath
    // (see build-logic/build.gradle.kts), so the id resolves from there.
    // alias(libs.plugins.*) does NOT work here: the version catalog is not
    // in scope inside a precompiled script plugin's plugins {} block.
    id("com.android.library")
}

// AGP 9 rule: org.jetbrains.kotlin.android is NEVER applied. AGP 9 builds
// Kotlin itself and registers the Kotlin extension below on its own.
extensions.configure<LibraryExtension> {
    compileSdk = 36

    defaultConfig {
        minSdk = 26
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    lint {
        warningsAsErrors = true
        abortOnError = true
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            all { useJUnitPlatform() }
        }
    }
}

extensions.configure<JavaPluginExtension> {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

extensions.configure<KotlinAndroidProjectExtension> {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        allWarningsAsErrors.set(true)
    }
}

dependencies {
    add("coreLibraryDesugaring", libs.findLibrary("desugar").get())
}
