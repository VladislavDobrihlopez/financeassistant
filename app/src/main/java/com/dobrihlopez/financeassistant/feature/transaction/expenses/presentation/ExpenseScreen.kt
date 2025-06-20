package com.dobrihlopez.financeassistant.feature.transaction.expenses.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.dobrihlopez.financeassistant.feature.transaction.history.presentation.HistoryScreen

@Composable
fun ExpenseScreen(component: ExpenseComponent) {
    Children(stack = component.childStack) { child ->
        when (val instance = child.instance) {
            is ExpenseComponent.Child.Main -> {
                ExpenseContent(
                    state = component.state.collectAsStateWithLifecycle().value,
                    onHistoryClick = component::onHistoryClick,
                    onExpenseClick = component::onExpenseClick,
                    onFabClick = {}
                )
            }

            is ExpenseComponent.Child.History -> {
                HistoryScreen(instance.component, paddingValues = PaddingValues(0.dp))
            }
        }
    }
}
