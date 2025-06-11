package com.dobrihlopez.financeassistant.feature.accounts.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AccountsScreen(componentComponent: AccountsComponent) {
    val screenState = componentComponent.state.collectAsStateWithLifecycle().value

    AccountContent(
        state = screenState,
        onEditClick = {},
        onFabClick = {},
        onBalanceClick = {},
        onCurrencyClick = {}
    )
}