package com.dobrihlopez.financeassistant.feature.transaction.history.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dobrihlopez.financeassistant.feature.transaction.history.presentation.HistoryComponent

@Composable
fun HistoryScreen(
    component: HistoryComponent,
    paddingValues: PaddingValues,
) {
    val state = component.state.collectAsStateWithLifecycle().value
    HistoryContent(
        state = state,
        onStartDateClick = component::onStartDateClick,
        onEndDateClick = component::onEndDateClick,
        onRefresh = component::onRefresh,
        paddingValues = paddingValues,
    )
}
