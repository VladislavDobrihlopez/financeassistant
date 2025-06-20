package com.dobrihlopez.financeassistant.feature.transaction.history.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.core_ui.composable.LoadingProgressBar
import com.dobrihlopez.financeassistant.core_ui.ui.theme.spacing
import com.dobrihlopez.financeassistant.feature.transaction.core.OverViewListItem
import com.dobrihlopez.financeassistant.feature.transaction.core.TransactionItem
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryContent(
    state: HistoryStore.State,
    onDateClick: (LocalDate) -> Unit,
    onRefresh: () -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = state.selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )
    val spacing = MaterialTheme.spacing

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> LoadingProgressBar()
            state.errorResId != null -> {
                Text(text = stringResource(id = state.errorResId))
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = spacing.medium, vertical = spacing.small),
                    contentPadding = PaddingValues(bottom = spacing.large)
                ) {
                    item {
                        OverViewListItem(
                            content = stringResource(R.string.history_start),
                            value = state.dateText,
                            onClick = { showDatePicker = true }
                        )
                        Spacer(modifier = Modifier.height(spacing.small))
                        OverViewListItem(
                            content = stringResource(R.string.history_end),
                            value = state.endText
                        )
                        Spacer(modifier = Modifier.height(spacing.small))
                        OverViewListItem(
                            content = stringResource(R.string.history_summary),
                            value = state.summaryValue
                        )
                        Spacer(modifier = Modifier.height(spacing.medium))
                    }
                    items(state.transactions, key = { it.id }) { transaction ->
                        TransactionItem(transaction, onClick = {})
                        Spacer(modifier = Modifier.height(spacing.small))
                    }
                }
            }
        }
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            val localDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            onDateClick(localDate)
                        }
                        showDatePicker = false
                    }) { Text(stringResource(android.R.string.ok)) }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) { Text(stringResource(android.R.string.cancel)) }
                }
            ) {
                DatePicker(state = datePickerState, showModeToggle = false)
            }
        }
    }
} 