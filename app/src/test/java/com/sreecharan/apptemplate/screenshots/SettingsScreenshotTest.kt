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
import java.io.File

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
        // Write goldens where the Screenshots workflow uploads them from.
        // The plugin's output dir may be absolute or relative to the module dir.
        val configuredDir = roborazziSystemPropertyOutputDirectory()
        val userDir = System.getProperty("user.dir") ?: "."
        val outputDir = if (File(configuredDir).isAbsolute) configuredDir else "$userDir/$configuredDir"
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
