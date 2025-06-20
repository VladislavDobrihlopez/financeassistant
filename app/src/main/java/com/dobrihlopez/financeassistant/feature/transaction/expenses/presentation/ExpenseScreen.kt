package com.dobrihlopez.financeassistant.feature.transaction.expenses.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ExpenseScreen(componentContext: ExpenseComponent) {
    val screenState = componentContext.state.collectAsStateWithLifecycle().value
    ExpenseContent(state = screenState, onHistoryClick = {
        componentContext.onHistoryClick()
    }, onFabClick = {
        componentContext.onFabClick()
    }, onExpenseClick = {
        componentContext.onExpenseClick(it)
    })
}
