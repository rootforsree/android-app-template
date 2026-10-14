package com.sreecharan.apptemplate.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class Note(
    val id: Long,
    val title: String,
    val body: String,
    val createdAt: Long,
)
