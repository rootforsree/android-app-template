package com.sreecharan.apptemplate.screenshots

import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.captureRoboImage
import com.github.takahirom.roborazzi.roborazziSystemPropertyOutputDirectory
import com.sreecharan.apptemplate.ui.about.AboutScreenContent
import com.sreecharan.apptemplate.ui.theme.AppTheme
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.robolectric.annotation.GraphicsMode
import org.robolectric.junit5.RobolectricExtension
import java.io.File

/**
 * Roborazzi golden tests for the about screen.
 * Uses Roborazzi's composable-lambda capture, which hosts the content in its
 * own activity, so no compose test rule or manifest entry is needed.
 * AboutScreenContent is driven with a fixed version string so goldens stay deterministic.
 */
@OptIn(ExperimentalRoborazziApi::class)
@ExtendWith(RobolectricExtension::class)
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
        val configuredDir = roborazziSystemPropertyOutputDirectory()
        val userDir = System.getProperty("user.dir") ?: "."
        val outputDir = if (File(configuredDir).isAbsolute) configuredDir else "$userDir/$configuredDir"
        captureRoboImage("$outputDir/$name") {
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
