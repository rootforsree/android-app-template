package com.sreecharan.apptemplate.ui.home

import androidx.compose.runtime.Immutable
import com.sreecharan.apptemplate.data.model.Note
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class HomeUiState(
    val isLoading: Boolean = true,
    val notes: ImmutableList<Note> = persistentListOf(),
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false,
)
