package com.dobrihlopez.financeassistant.feature.transaction.income.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.dobrihlopez.financeassistant.feature.transaction.history.presentation.HistoryScreen

@Composable
fun IncomeScreen(component: IncomeComponent) {
    val childStack = component.childStack
    Children(stack = childStack) { child ->
        when (val instance = child.instance) {
            is IncomeComponent.Child.Main -> {
                IncomeContent(
                    state = component.state.collectAsStateWithLifecycle().value,
                    onHistoryClick = component::onHistoryClick,
                    onFabClick = {}
                )
            }

            is IncomeComponent.Child.History -> {
                HistoryScreen(instance.component)
            }
        }
    }
}
