package com.dobrihlopez.financeassistant.feature.transaction.history.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HistoryScreen(component: HistoryComponent) {
    val state = component.state.collectAsStateWithLifecycle().value
    HistoryContent(
        state = state,
        onDateClick = component::onDateClick,
        onRefresh = component::onRefresh
    )
} 