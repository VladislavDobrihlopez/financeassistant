package com.dobrihlopez.financeassistant.feature.income.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.coreui.composable.LoadingProgressBar
import com.dobrihlopez.financeassistant.coreui.ui.theme.FinanceAssistantTheme
import com.dobrihlopez.financeassistant.feature.transactioncore.TransactionItem
import com.dobrihlopez.financeassistant.feature.transactioncore.OverViewListItem
import com.dobrihlopez.financeassistant.feature.transactioncore.TransactionScreenState
import com.dobrihlopez.financeassistant.feature.transactioncore.previewTransactions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseContent(
    state: TransactionScreenState,
    onHistoryClick: () -> Unit,
    onFabClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(text = "Расходы сегодня", style = MaterialTheme.typography.titleLarge)
                    }
                },
                actions = {
                    IconButton(onClick = onHistoryClick) {
                        Icon(ImageVector.vectorResource(R.drawable.ic_history), contentDescription = "History")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = onFabClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add an item")
            }
        }
    ) { paddingValues ->
        when (state) {
            is TransactionScreenState.Loading -> LoadingProgressBar()
            is TransactionScreenState.Failed -> TODO()
            is TransactionScreenState.Succeeded -> {
                LazyColumn(modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                ) {
                    item {
                        OverViewListItem(content = state.summaryText, value = state.summaryValue)
                        HorizontalDivider()
                    }
                    items(state.transactions) { transaction ->
                        TransactionItem(transaction, onClick = { TODO() })
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Expense Light")
@Composable
private fun PreviewIncomeLight() {
    FinanceAssistantTheme(darkTheme = false) {
        ExpenseContent(
            state = TransactionScreenState.Succeeded(
                transactions = previewTransactions(),
                summaryText = "Всего",
                summaryValue = "436 558 ₽"
            ),
            onHistoryClick = {},
            onFabClick = {},
        )
    }
}
