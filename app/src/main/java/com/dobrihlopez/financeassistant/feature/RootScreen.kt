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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.dobrihlopez.financeassistant.core_ui.composable.InternetConnectionStatus
import com.dobrihlopez.financeassistant.coreui.ui.theme.spacing
import com.dobrihlopez.financeassistant.feature.accounts.presentation.AccountsScreen
import com.dobrihlopez.financeassistant.feature.categories.presentation.screen.CategoriesScreen
import com.dobrihlopez.financeassistant.feature.settings.presentation.screen.SettingScreen
import com.dobrihlopez.financeassistant.feature.transaction.expenses.presentation.ExpenseScreen
import com.dobrihlopez.financeassistant.feature.transaction.income.presentation.IncomeScreen
import com.dobrihlopez.financeassistant.navigation.BottomNavigationBar
import com.dobrihlopez.financeassistant.navigation.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen(rootComponent: RootComponent, hasInternetState: State<Boolean>) {
    val childStack by rootComponent.state.subscribeAsState()

    val snackBarHost = remember {
        SnackbarHostState()
    }

    val spacing = MaterialTheme.spacing

    val hasInternet = hasInternetState.value
    LaunchedEffect(hasInternet) {
        if (!hasInternet) {
            snackBarHost.showSnackbar(
                message = "internet issues",
                duration = SnackbarDuration.Indefinite,
                withDismissAction = false
            )
        } else {
            snackBarHost.currentSnackbarData?.dismiss()
        }
    }

    Scaffold(
        modifier =
            Modifier.fillMaxSize()
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
                },
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHost,
                snackbar = { data ->
                    InternetConnectionStatus(
                        hasInternet = hasInternet,
                        modifier = Modifier.padding(horizontal = spacing.medium)
                    )
                }
            )
        },
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
        ) {
            Children(stack = rootComponent.state) { screen ->
                when (val config = screen.instance) {
                    is RootComponent.Child.Accounts -> AccountsScreen(config.component)
                    is RootComponent.Child.Category -> CategoriesScreen(config.component)
                    is RootComponent.Child.Expenses -> ExpenseScreen(config.component)
                    is RootComponent.Child.Income -> IncomeScreen(config.component)
                    is RootComponent.Child.Settings -> SettingScreen(config.component)
                }
            }
        }
    }
}
