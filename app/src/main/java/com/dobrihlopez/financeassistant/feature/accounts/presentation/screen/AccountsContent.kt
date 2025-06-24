package com.dobrihlopez.financeassistant.feature.accounts.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.coreui.composable.ErrorSnackbarHost
import com.dobrihlopez.financeassistant.coreui.composable.Fab
import com.dobrihlopez.financeassistant.coreui.composable.LoadingProgressBar
import com.dobrihlopez.financeassistant.coreui.ui.theme.FinanceAssistantTheme
import com.dobrihlopez.financeassistant.feature.accounts.domain.model.UserAccountDetailed
import com.dobrihlopez.financeassistant.feature.accounts.presentation.AccountsStore.AccountScreenState
import com.dobrihlopez.financeassistant.feature.accounts.presentation.composable.AccountItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountContent(
    state: AccountScreenState,
    onEditClick: () -> Unit,
    onFabClick: () -> Unit,
    onBalanceClick: () -> Unit,
    onCurrencyClick: () -> Unit,
    onRetry: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(R.string.account_topbar_title),
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                },
                actions = {
                    AnimatedVisibility(
                        visible = state is AccountScreenState.Succeeded,
                        enter = fadeIn() + expandHorizontally(
                            clip = false,
                            expandFrom = Alignment.Start
                        ),
                        exit = fadeOut() + shrinkHorizontally(clip = false)
                    ) {
                        IconButton(onClick = onEditClick) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                        }
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                    ),
            )
        },
        floatingActionButton = {
            val onClick = remember(state) {
                if (state is AccountScreenState.Succeeded) onFabClick else null
            }
            Fab(onClick = onClick)
        },
        snackbarHost = {
            if (state is AccountScreenState.Failed) {
                ErrorSnackbarHost(
                    errorResId = state.errorResId,
                    onRetry = onRetry,
                )
            }
        },
    ) { innerPadding ->
        when (state) {
            is AccountScreenState.Loading -> LoadingProgressBar()
            is AccountScreenState.Failed -> {}
            is AccountScreenState.Succeeded -> {
                val account = state.account
                val items =
                    remember(state.account) {
                        listOf(
                            AccountActionItem(
                                title = R.string.account_balance,
                                value = account.balance,
                                currency = account.currency,
                                onClick = onBalanceClick,
                                emoji = "\uD83D\uDCB0",
                            ),
                            AccountActionItem(
                                title = R.string.account_currency,
                                currency = account.currency,
                                onClick = onCurrencyClick,
                            ),
                        )
                    }
                LazyColumn(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(innerPadding),
                ) {
                    items(items = items, key = { it.id }) { item ->
                        AccountItem(item, onClick = { item.onClick() })
                        HorizontalDivider()
                    }
                    // TODO: график заботать кастомную вьюху на канвасе
                }
            }
        }
    }
}

private fun provideAccount(): UserAccountDetailed =
    UserAccountDetailed(
        id = 1,
        name = "Мой счёт",
        balance = "-670 000",
        currency = "₽",
        createdAt = "",
        updatedAt = "",
        expenseStats = emptyList(),
        incomeStatistics = emptyList(),
    )

@Preview(
    name = "Light Theme - RU",
    group = "Russian",
    locale = "ru",
    showBackground = true,
)
@Composable
private fun PreviewLightRussian() {
    FinanceAssistantTheme(darkTheme = false) {
        AccountContent(
            state =
                AccountScreenState.Succeeded(
                    account = provideAccount(),
                ),
            onEditClick = {},
            onFabClick = {},
            onBalanceClick = {},
            onCurrencyClick = {},
        )
    }
}

@Preview(
    name = "Dark Theme - RU",
    group = "Russian",
    locale = "ru",
    showBackground = true,
)
@Composable
private fun PreviewDarkRussian() {
    FinanceAssistantTheme(darkTheme = true) {
        AccountContent(
            state =
                AccountScreenState.Succeeded(
                    account = provideAccount(),
                ),
            onEditClick = {},
            onFabClick = {},
            onBalanceClick = {},
            onCurrencyClick = {},
        )
    }
}

@Preview(
    name = "Light Theme - EN",
    group = "English",
    locale = "en",
    showBackground = true,
)
@Composable
private fun PreviewLightEnglish() {
    FinanceAssistantTheme(darkTheme = false) {
        AccountContent(
            state =
                AccountScreenState.Succeeded(
                    account = provideAccount(),
                ),
            onEditClick = {},
            onFabClick = {},
            onBalanceClick = {},
            onCurrencyClick = {},
        )
    }
}

@Preview(
    name = "Dark Theme - EN",
    group = "English",
    locale = "en",
    showBackground = true,
)
@Composable
private fun PreviewDarkEnglish() {
    FinanceAssistantTheme(darkTheme = true) {
        AccountContent(
            state =
                AccountScreenState.Succeeded(
                    account = provideAccount(),
                ),
            onEditClick = {},
            onFabClick = {},
            onBalanceClick = {},
            onCurrencyClick = {},
        )
    }
}
