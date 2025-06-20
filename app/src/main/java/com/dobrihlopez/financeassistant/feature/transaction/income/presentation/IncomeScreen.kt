package com.dobrihlopez.financeassistant.feature.transaction.income.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun IncomeScreen(componentContext: IncomeComponent) {
    val screenState = componentContext.state.collectAsStateWithLifecycle().value
    IncomeContent(state = screenState, onHistoryClick = componentContext::onHistoryClick, onFabClick = {})
}
