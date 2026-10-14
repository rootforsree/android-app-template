package com.sreecharan.apptemplate.ui.settings

import androidx.compose.runtime.Immutable
import com.sreecharan.apptemplate.data.model.ThemeMode

@Immutable
data class SettingsUiState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val hapticsEnabled: Boolean = true,
)
