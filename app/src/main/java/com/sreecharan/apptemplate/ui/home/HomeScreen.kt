package com.sreecharan.apptemplate.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sreecharan.apptemplate.R
import com.sreecharan.apptemplate.data.model.Note
import com.sreecharan.apptemplate.ui.components.EmptyState
import com.sreecharan.apptemplate.ui.components.ErrorState
import com.sreecharan.apptemplate.ui.components.GroupCard
import com.sreecharan.apptemplate.ui.components.InsetGroupedList
import com.sreecharan.apptemplate.ui.components.SkeletonList
import com.sreecharan.apptemplate.ui.theme.AppColors
import com.sreecharan.apptemplate.ui.theme.AppDimens
import com.sreecharan.apptemplate.ui.theme.AppTypography
import com.sreecharan.apptemplate.ui.theme.LocalAppColors
import com.sreecharan.apptemplate.ui.theme.rememberHapticTick
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Thin stateful route: owns the ViewModel, collects with
 * collectAsStateWithLifecycle, and forwards events. The content below stays
 * stateless and never sees the ViewModel.
 */
@Composable
fun HomeRoute(viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreenContent(
        state = state,
        onRefresh = viewModel::onRefresh,
        onRetry = viewModel::onRetry,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    state: HomeUiState,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalAppColors.current
    val tick = rememberHapticTick(medium = true)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { HomeTopBar(scrollBehavior = scrollBehavior, colors = colors) },
        containerColor = colors.groupedBackground,
    ) { padding ->
        if (state.isLoading) {
            SkeletonList(
                contentPadding =
                    PaddingValues(
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding() + AppDimens.tabBarClearance,
                    ),
            )
        } else {
            PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                onRefresh = {
                    tick()
                    onRefresh()
                },
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(colors.groupedBackground)
                        .padding(
                            top = padding.calculateTopPadding(),
                            bottom = padding.calculateBottomPadding(),
                        ),
            ) {
                HomeContent(
                    state = state,
                    onRefresh = onRefresh,
                    onRetry = onRetry,
                )
            }
        }
    }
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalAppColors.current
    val message = state.errorMessage
    when {
        message != null -> {
            ErrorState(
                message = message,
                onRetry = onRetry,
                modifier = modifier.padding(bottom = AppDimens.tabBarClearance),
            )
        }

        state.notes.isEmpty() -> {
            EmptyState(
                message = stringResource(R.string.home_empty_message),
                actionLabel = stringResource(R.string.action_refresh),
                onAction = onRefresh,
                icon = Icons.Outlined.Description,
                modifier = modifier.padding(bottom = AppDimens.tabBarClearance),
            )
        }

        else -> {
            InsetGroupedList(
                contentPadding = PaddingValues(bottom = AppDimens.tabBarClearance),
            ) {
                items(
                    count = state.notes.size,
                    key = { index -> state.notes[index].id },
                    contentType = { "note" },
                ) { index ->
                    val note = state.notes[index]
                    GroupCard {
                        NoteRow(note = note)
                    }
                }
            }
        }
    }
}

@Composable
private fun NoteRow(
    note: Note,
    modifier: Modifier = Modifier,
) {
    val colors = LocalAppColors.current
    Column(
        modifier = modifier.padding(AppDimens.spacing16),
        verticalArrangement = Arrangement.spacedBy(AppDimens.spacing4),
    ) {
        Text(
            text = note.title,
            style = AppTypography.headline,
            color = colors.label,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = note.body,
            style = AppTypography.subheadline,
            color = colors.secondaryLabel,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = formatNoteDate(note.createdAt),
            style = AppTypography.caption,
            color = colors.secondaryLabel,
        )
    }
}

private fun formatNoteDate(epochMillis: Long): String =
    Instant
        .ofEpochMilli(epochMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.getDefault()))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    colors: AppColors,
) {
    LargeTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.home_title),
                style = AppTypography.largeTitle,
            )
        },
        scrollBehavior = scrollBehavior,
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = colors.groupedBackground,
                scrolledContainerColor = colors.groupedBackground,
            ),
    )
}
