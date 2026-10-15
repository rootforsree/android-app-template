package com.sreecharan.apptemplate.screenshots

import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.RoborazziTaskType
import com.github.takahirom.roborazzi.captureRoboImage
import com.sreecharan.apptemplate.data.model.Note
import com.sreecharan.apptemplate.ui.home.HomeScreenContent
import com.sreecharan.apptemplate.ui.home.HomeUiState
import com.sreecharan.apptemplate.ui.theme.AppTheme
import kotlinx.collections.immutable.persistentListOf
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode

/**
 * Roborazzi golden tests for the home screen.
 * Uses Roborazzi's composable-lambda capture, which hosts the content in its
 * own activity, so no compose test rule or manifest entry is needed.
 * All states are fabricated deterministic fixtures; no repository, no network.
 */
@OptIn(ExperimentalRoborazziApi::class)
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class HomeScreenshotTest {
    private val sampleNotes =
        persistentListOf(
            Note(id = 1, title = "Grocery list", body = "Eggs, milk, coffee", createdAt = 1_700_000_000_000),
            Note(id = 2, title = "Ideas", body = "Build the thing", createdAt = 1_700_000_001_000),
            Note(id = 3, title = "Reminder", body = "Call mom", createdAt = 1_700_000_002_000),
        )

    @Test
    fun home_light() {
        capture(
            darkTheme = false,
            state = HomeUiState(isLoading = false, notes = sampleNotes)
        )
    }

    @Test
    fun home_dark() {
        capture(
            darkTheme = true,
            state = HomeUiState(isLoading = false, notes = sampleNotes)
        )
    }

    @Test
    fun home_loading() {
        capture(
            darkTheme = false,
            state = HomeUiState(isLoading = true)
        )
    }

    @Test
    fun home_empty() {
        capture(
            darkTheme = false,
            state = HomeUiState(isLoading = false, notes = persistentListOf())
        )
    }

    @Test
    fun home_error() {
        capture(
            darkTheme = false,
            state =
                HomeUiState(
                    isLoading = false,
                    notes = persistentListOf(),
                    errorMessage = "Couldn't load notes. Check your connection and try again.",
                )
        )
    }

    private fun capture(
        darkTheme: Boolean,
        state: HomeUiState,
    ) {
        // Record mode is set explicitly: the Gradle plugin's system properties
        // do not reliably reach the test JVM.
        captureRoboImage(
            roborazziOptions = RoborazziOptions(taskType = RoborazziTaskType.Record),
        ) {
            AppTheme(darkTheme = darkTheme, hapticsEnabled = false) {
                HomeScreenContent(
                    state = state,
                    onRefresh = {},
                    onRetry = {},
                )
            }
        }
    }
}
