package com.dobrihlopez.financeassistant.feature.transaction.expenses.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dobrihlopez.financeassistant.core.Transaction
import com.dobrihlopez.financeassistant.core_ui.composable.LoadingProgressBar
import com.dobrihlopez.financeassistant.core_ui.ui.theme.FinanceAssistantTheme
import com.dobrihlopez.financeassistant.feature.transaction.core.OverViewListItem
import com.dobrihlopez.financeassistant.feature.transaction.core.TransactionItem
import com.dobrihlopez.financeassistant.feature.transaction.core.previewTransactions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseContent(
    state: ExpenseStore.ExpenseScreenState,
    onExpenseClick: (Transaction) -> Unit,
    paddingValues: PaddingValues,
) {
    when (state) {
        is ExpenseStore.ExpenseScreenState.Loading -> LoadingProgressBar()
        is ExpenseStore.ExpenseScreenState.Failed -> TODO()
        is ExpenseStore.ExpenseScreenState.Succeeded -> {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                item {
                    OverViewListItem(content = state.summaryText, value = state.summaryValue)
                    HorizontalDivider()
                }
                items(state.transactions, key = { it.id }) { transaction ->
                    TransactionItem(transaction, onClick = {
                        onExpenseClick(transaction)
                    })
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Expense Light")
@Composable
private fun PreviewExpenseLight() {
    FinanceAssistantTheme(darkTheme = false) {
        ExpenseContent(
            state = ExpenseStore.ExpenseScreenState.Succeeded(
                transactions = previewTransactions(),
                summaryText = "Всего",
                summaryValue = "436 558 ₽"
            ),
            onExpenseClick = {},
            paddingValues = PaddingValues(0.dp)
        )
    }
}

@Preview(showBackground = true, name = "Expense Dark")
@Composable
private fun PreviewExpenseDark() {
    FinanceAssistantTheme(darkTheme = true) {
        ExpenseContent(
            state = ExpenseStore.ExpenseScreenState.Succeeded(
                transactions = previewTransactions(),
                summaryText = "Всего",
                summaryValue = "436 558 ₽"
            ),
            onExpenseClick = {},
            paddingValues = PaddingValues(0.dp)
        )
    }
}
