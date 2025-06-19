package com.dobrihlopez.financeassistant.feature.accounts.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.dobrihlopez.financeassistant.feature.accounts.presentation.composable.BalanceEditDialog
import com.dobrihlopez.financeassistant.feature.accounts.presentation.composable.CurrencyChooser
import com.dobrihlopez.financeassistant.feature.accounts.presentation.AccountsStore.AccountScreenState
import com.dobrihlopez.financeassistant.feature.accounts.presentation.AccountsStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(component: AccountsComponent) {
    val state by component.state.collectAsState()
    var showCurrencySheet by remember { mutableStateOf(false) }
    var showBalanceSheet by remember { mutableStateOf(false) }
    val currencySheetState = rememberModalBottomSheetState()
    val balanceSheetState = rememberModalBottomSheetState()

    AccountContent(
        state = state,
        onEditClick = { component.onEditClick() },
        onFabClick = { component.onFabClick() },
        onBalanceClick = { showBalanceSheet = true },
        onCurrencyClick = { showCurrencySheet = true }
    )

    if (showCurrencySheet) {
        CurrencyChooser(
            sheetState = currencySheetState,
            onDismiss = { showCurrencySheet = false },
            onCurrencySelected = { currency ->
                val account = (state as? AccountScreenState.Succeeded)?.account ?: return@CurrencyChooser
                component.onCurrencySelected(account, currency)
                showCurrencySheet = false
            }
        )
    }

    if (showBalanceSheet) {
        BalanceEditDialog(
            sheetState = balanceSheetState,
            initialBalance = (state as? AccountScreenState.Succeeded)?.account?.balance ?: "",
            onDismiss = { showBalanceSheet = false },
            onBalanceChanged = { newBalance ->
                val account = (state as? AccountScreenState.Succeeded)?.account ?: return@BalanceEditDialog
                component.onBalanceChanged(account, newBalance)
                showBalanceSheet = false
            }
        )
    }
}