package com.sreecharan.apptemplate.screenshots

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.sreecharan.apptemplate.data.model.ThemeMode
import com.sreecharan.apptemplate.ui.settings.SettingsScreenContent // ASSUMPTION: UI worker places this in ui.settings
import com.sreecharan.apptemplate.ui.settings.SettingsUiState
import com.sreecharan.apptemplate.ui.theme.AppTheme
import io.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode

/**
 * Roborazzi golden tests for the settings screen.
 * Deterministic fixtures only; haptics disabled so no platform side effects.
 * Verified against Roborazzi 1.70.0 API (pinned in libs.versions.toml) (io.github.takahirom.roborazzi).
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class SettingsScreenshotTest {
    @get:Rule
    val composeRule = createComposeRule()

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
        composeRule.setContent {
            AppTheme(darkTheme = darkTheme, hapticsEnabled = false) {
                SettingsScreenContent(
                    state = state,
                    onThemeModeSelected = {},
                    onHapticsToggled = {},
                    onOpenNotificationSettings = {},
                )
            }
        }
        composeRule.onRoot().captureRoboImage(name)
    }
}
