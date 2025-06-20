package com.dobrihlopez.financeassistant.feature.transaction.income.presentation

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
import com.dobrihlopez.financeassistant.core_ui.composable.LoadingProgressBar
import com.dobrihlopez.financeassistant.core_ui.ui.theme.FinanceAssistantTheme
import com.dobrihlopez.financeassistant.feature.transaction.core.OverViewListItem
import com.dobrihlopez.financeassistant.feature.transaction.core.TransactionItem
import com.dobrihlopez.financeassistant.feature.transaction.core.previewIncomeTransactions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeContent(
    state: IncomeStore.IncomeScreenState,
    paddingValues: PaddingValues,
) {

    when (state) {
        is IncomeStore.IncomeScreenState.Loading -> LoadingProgressBar()
        is IncomeStore.IncomeScreenState.Failed -> {}
        is IncomeStore.IncomeScreenState.Succeeded -> {
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
                    TransactionItem(transaction, onClick = { })
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Income Light")
@Composable
private fun PreviewIncomeLight() {
    FinanceAssistantTheme(darkTheme = false) {
        IncomeContent(
            state = IncomeStore.IncomeScreenState.Succeeded(
                transactions = previewIncomeTransactions(),
                summaryText = "Всего",
                summaryValue = "600 000 ₽"
            ),
            paddingValues = PaddingValues(0.dp)
        )
    }
}

@Preview(showBackground = true, name = "Income Dark")
@Composable
private fun PreviewIncomeDark() {
    FinanceAssistantTheme(darkTheme = true) {
        IncomeContent(
            state = IncomeStore.IncomeScreenState.Succeeded(
                transactions = previewIncomeTransactions(),
                summaryText = "Всего",
                summaryValue = "600 000 ₽"
            ),
            paddingValues = PaddingValues(0.dp)
        )
    }
}


