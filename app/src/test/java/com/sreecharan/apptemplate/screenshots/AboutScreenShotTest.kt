package com.sreecharan.apptemplate.screenshots

import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.RoborazziTaskType
import com.github.takahirom.roborazzi.captureRoboImage
import com.github.takahirom.roborazzi.roborazziSystemPropertyOutputDirectory
import com.sreecharan.apptemplate.ui.about.AboutScreenContent
import com.sreecharan.apptemplate.ui.theme.AppTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode
import java.io.File

/**
 * Roborazzi golden tests for the about screen.
 * Uses Roborazzi's composable-lambda capture, which hosts the content in its
 * own activity, so no compose test rule or manifest entry is needed.
 * AboutScreenContent is driven with a fixed version string so goldens stay deterministic.
 */
@OptIn(ExperimentalRoborazziApi::class)
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class AboutScreenShotTest {
    @Test
    fun about_light() {
        capture(darkTheme = false, name = "about_light")
    }

    @Test
    fun about_dark() {
        capture(darkTheme = true, name = "about_dark")
    }

    private fun capture(
        darkTheme: Boolean,
        name: String,
    ) {
        // Write goldens where the Screenshots workflow uploads them from.
        // The plugin's output dir may be absolute or relative to the module dir.
        // Record mode is set explicitly: the Gradle plugin's system properties
        // do not reliably reach the test JVM.
        val configuredDir = roborazziSystemPropertyOutputDirectory()
        val userDir = System.getProperty("user.dir") ?: "."
        val outputDir = if (File(configuredDir).isAbsolute) configuredDir else "$userDir/$configuredDir"
        captureRoboImage(
            filePath = "$outputDir/$name",
            roborazziOptions = RoborazziOptions(taskType = RoborazziTaskType.Record),
        ) {
            AppTheme(darkTheme = darkTheme, hapticsEnabled = false) {
                AboutScreenContent(
                    appVersion = "1.0.0-dev",
                    onOpenGitHub = {},
                    onShareApp = {},
                    onRateApp = {},
                )
            }
        }
    }
}
