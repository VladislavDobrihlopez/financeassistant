package com.dobrihlopez.financeassistant.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.dobrihlopez.financeassistant.feature.accounts.presentation.AccountsScreen
import com.dobrihlopez.financeassistant.feature.categories.presentation.CategoriesScreen
import com.dobrihlopez.financeassistant.feature.settings.presentation.SettingScreen
import com.dobrihlopez.financeassistant.feature.transaction_core.expenses.presentation.ExpenseScreen
import com.dobrihlopez.financeassistant.feature.transaction_core.income.presentation.IncomeScreen

@Composable
fun RootScreen(rootComponent: RootComponent) {
    Box(Modifier.fillMaxSize()) {
        Children(stack = rootComponent.state) { screen ->
            when (val config = screen.instance) {
                is RootComponent.Child.Accounts -> AccountsScreen(config.accountComponent)
                is RootComponent.Child.Category -> CategoriesScreen(config.categoryComponent)
                is RootComponent.Child.Expenses -> ExpenseScreen(config.expensesComponent)
                is RootComponent.Child.Income -> IncomeScreen(config.incomeComponent)
                is RootComponent.Child.Settings -> SettingScreen(config.settingsComponent)
            }
        }
    }
}
