package com.sreecharan.apptemplate.screenshots

import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.captureRoboImage
import com.sreecharan.apptemplate.ui.about.AboutScreenContent
import com.sreecharan.apptemplate.ui.theme.AppTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode

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
        captureRoboImage(name) {
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
