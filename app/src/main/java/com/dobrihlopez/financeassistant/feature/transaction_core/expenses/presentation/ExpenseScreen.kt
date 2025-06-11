package com.dobrihlopez.financeassistant.feature.transaction_core.expenses.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dobrihlopez.financeassistant.feature.income.presentation.ExpenseContent

@Composable
fun ExpenseScreen(componentContext: ExpenseComponent) {
    val screenState = componentContext.state.collectAsStateWithLifecycle().value
    ExpenseContent(state = screenState, onHistoryClick = {}, onFabClick = {})
}
