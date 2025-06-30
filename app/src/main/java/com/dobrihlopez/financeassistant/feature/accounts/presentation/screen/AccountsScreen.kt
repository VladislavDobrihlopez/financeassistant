package com.dobrihlopez.financeassistant.feature.accounts.presentation.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dobrihlopez.financeassistant.core_ui.composable.dialog.BalanceEditDialog
import com.dobrihlopez.financeassistant.feature.accounts.presentation.AccountsComponent
import com.dobrihlopez.financeassistant.feature.accounts.presentation.AccountsStore.AccountScreenState
import com.dobrihlopez.financeassistant.feature.accounts.presentation.composable.CurrencyChooser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(component: AccountsComponent) {
    val state by component.state.collectAsStateWithLifecycle()
    var showCurrencySheet by rememberSaveable { mutableStateOf(false) }
    var showBalanceSheet by rememberSaveable { mutableStateOf(false) }
    val currencySheetState = rememberModalBottomSheetState()
    val balanceSheetState = rememberModalBottomSheetState()

    AccountContent(
        state = state,
        onEditClick = {
            showCurrencySheet = false
            showBalanceSheet = true
        },
        onFabClick = { component.onFabClick() },
        onBalanceClick = {
            showCurrencySheet = false
            showBalanceSheet = true
        },
        onCurrencyClick = {
            showBalanceSheet = false
            showCurrencySheet = true
        },
    )

    if (showCurrencySheet) {
        CurrencyChooser(
            sheetState = currencySheetState,
            onDismiss = { showCurrencySheet = false },
            onCurrencySelected = { currency ->
                val account = (state as? AccountScreenState.Succeeded)?.account ?: return@CurrencyChooser
                component.onCurrencySelected(account, currency)
                showCurrencySheet = false
            },
        )
    }

    if (showBalanceSheet) {
        BalanceEditDialog(
            sheetState = balanceSheetState,
            initialBalance = (state as? AccountScreenState.Succeeded)?.account?.balance ?: "",
            onDismiss = { showBalanceSheet = false },
            onDone = { newBalance ->
                val account = (state as? AccountScreenState.Succeeded)?.account ?: return@BalanceEditDialog
                component.onBalanceChanged(account, newBalance)
                showBalanceSheet = false
            },
        )
    }
}
