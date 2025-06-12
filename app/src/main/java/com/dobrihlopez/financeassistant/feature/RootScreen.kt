package com.dobrihlopez.financeassistant.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.dobrihlopez.financeassistant.core_ui.composable.BottomNavigationBar
import com.dobrihlopez.financeassistant.core_ui.composable.NavigationItem
import com.dobrihlopez.financeassistant.feature.accounts.presentation.AccountsScreen
import com.dobrihlopez.financeassistant.feature.categories.presentation.CategoriesScreen
import com.dobrihlopez.financeassistant.feature.settings.presentation.SettingScreen
import com.dobrihlopez.financeassistant.feature.transaction_core.expenses.presentation.ExpenseScreen
import com.dobrihlopez.financeassistant.feature.transaction_core.income.presentation.IncomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen(rootComponent: RootComponent) {
    val childStack by rootComponent.state.subscribeAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .windowInsetsPadding(WindowInsets.statusBars)
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.navigationBars),
        bottomBar = {
            BottomNavigationBar(
                currentRoute = childStack.active.instance,
                onNavigate = { item ->
                    when (item) {
                        is NavigationItem.Expenses -> rootComponent.onExpensesClick()
                        is NavigationItem.Incomes -> rootComponent.onIncomeClick()
                        is NavigationItem.Accounts -> rootComponent.onAccountsClick()
                        is NavigationItem.Categories -> rootComponent.onCategoriesClick()
                        is NavigationItem.Settings -> rootComponent.onSettingsClick()
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
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
}
