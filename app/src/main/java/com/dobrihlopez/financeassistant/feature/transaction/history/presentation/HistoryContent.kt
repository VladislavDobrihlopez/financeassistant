package com.dobrihlopez.financeassistant.feature.transaction.history.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.core.atEndOfDay
import com.dobrihlopez.financeassistant.core_ui.composable.LoadingProgressBar
import com.dobrihlopez.financeassistant.feature.transaction.core_ui.OverViewListItem
import com.dobrihlopez.financeassistant.feature.transaction.core_ui.TransactionItem
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryContent(
    state: HistoryStore.State,
    onStartDateClick: (LocalDate) -> Unit,
    onEndDateClick: (LocalDate) -> Unit,
    onRefresh: () -> Unit,
    paddingValues: PaddingValues,
) {
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    val startDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = state.startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )
    val endDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = state.endDate.atEndOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )

    Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
        when {
            state.isLoading -> LoadingProgressBar()
            state.errorResId != null -> {
                Text(text = stringResource(id = state.errorResId))
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    item {
                        OverViewListItem(
                            content = stringResource(R.string.history_start),
                            value = state.startText,
                            onClick = { showStartDatePicker = true }
                        )
                        HorizontalDivider()
                    }
                    item {
                        OverViewListItem(
                            content = stringResource(R.string.history_end),
                            value = state.endText,
                            onClick = { showEndDatePicker = true }
                        )
                        HorizontalDivider()
                    }
                    item {
                        OverViewListItem(
                            content = stringResource(R.string.history_summary),
                            value = state.summaryValue
                        )
                        HorizontalDivider()
                    }
                    items(state.transactions, key = { it.id }) { transaction ->
                        TransactionItem(transaction, onClick = {}, showTime = true)
                        HorizontalDivider()
                    }
                }
            }
        }
        if (showStartDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showStartDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        val millis = startDatePickerState.selectedDateMillis
                        if (millis != null) {
                            val localDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            onStartDateClick(localDate)
                        }
                        showStartDatePicker = false
                    }) { Text(stringResource(android.R.string.ok)) }
                },
                dismissButton = {
                    TextButton(onClick = { showStartDatePicker = false }) { Text(stringResource(android.R.string.cancel)) }
                }
            ) {
                DatePicker(state = startDatePickerState, showModeToggle = false)
            }
        }
        if (showEndDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showEndDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        val millis = endDatePickerState.selectedDateMillis
                        if (millis != null) {
                            val localDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            onEndDateClick(localDate)
                        }
                        showEndDatePicker = false
                    }) { Text(stringResource(android.R.string.ok)) }
                },
                dismissButton = {
                    TextButton(onClick = { showEndDatePicker = false }) { Text(stringResource(android.R.string.cancel)) }
                }
            ) {
                DatePicker(state = endDatePickerState, showModeToggle = false)
            }
        }
    }
} 