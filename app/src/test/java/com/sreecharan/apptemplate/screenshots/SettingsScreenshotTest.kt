package com.sreecharan.apptemplate.screenshots

import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.captureRoboImage
import com.github.takahirom.roborazzi.roborazziSystemPropertyOutputDirectory
import com.sreecharan.apptemplate.data.model.ThemeMode
import com.sreecharan.apptemplate.ui.settings.SettingsScreenContent
import com.sreecharan.apptemplate.ui.settings.SettingsUiState
import com.sreecharan.apptemplate.ui.theme.AppTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode

/**
 * Roborazzi golden tests for the settings screen.
 * Uses Roborazzi's composable-lambda capture, which hosts the content in its
 * own activity, so no compose test rule or manifest entry is needed.
 * Deterministic fixtures only; haptics disabled so no platform side effects.
 */
@OptIn(ExperimentalRoborazziApi::class)
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class SettingsScreenshotTest {
    @Test
    fun settings_light() {
        capture(
            darkTheme = false,
            state = SettingsUiState(),
            name = "settings_light",
        )
    }

    @Test
    fun settings_dark() {
        capture(
            darkTheme = true,
            state = SettingsUiState(themeMode = ThemeMode.DARK, hapticsEnabled = true),
            name = "settings_dark",
        )
    }

    private fun capture(
        darkTheme: Boolean,
        state: SettingsUiState,
        name: String,
    ) {
        // Write into the Roborazzi task output dir so the Screenshots workflow
        // can upload the goldens; the path is relative to the module directory.
        val outputDir = roborazziSystemPropertyOutputDirectory()
        captureRoboImage("$outputDir/$name") {
            AppTheme(darkTheme = darkTheme, hapticsEnabled = false) {
                SettingsScreenContent(
                    state = state,
                    onThemeModeSelected = {},
                    onHapticsToggled = {},
                    onOpenNotificationSettings = {},
                )
            }
        }
    }
}
