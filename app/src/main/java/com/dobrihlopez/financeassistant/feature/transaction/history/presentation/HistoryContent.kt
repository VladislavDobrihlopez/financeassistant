package com.dobrihlopez.financeassistant.feature.transaction.history.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.coreui.composable.ErrorSnackbarHost
import com.dobrihlopez.financeassistant.coreui.composable.LoadingProgressBar
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
    onRetry: () -> Unit = {},
    onRefresh: () -> Unit,
    paddingValues: PaddingValues,
) {
    var showStartDatePicker by rememberSaveable { mutableStateOf(false) }
    var showEndDatePicker by rememberSaveable { mutableStateOf(false) }
    val startDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = state.startDate
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )
    val endDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = state.endDate
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )
    Scaffold(
        snackbarHost = {
            if (state.errorResId != null) {
                ErrorSnackbarHost(
                    errorResId = state.errorResId,
                    onRetry = onRetry,
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(innerPadding)
        ) {
            when {
                state.isLoading -> LoadingProgressBar()
                state.errorResId != null -> {
                    Text(text = stringResource(id = state.errorResId))
                }

                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            OverViewListItem(
                                content = stringResource(R.string.history_start),
                                value = state.startText,
                                onClick = { showStartDatePicker = true },
                            )
                            HorizontalDivider()
                        }
                        item {
                            OverViewListItem(
                                content = stringResource(R.string.history_end),
                                value = state.endText,
                                onClick = { showEndDatePicker = true },
                            )
                            HorizontalDivider()
                        }
                        item {
                            OverViewListItem(
                                content = stringResource(R.string.history_summary),
                                value = state.summaryValue,
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
                            startDatePickerState.selectedDateMillis?.let {
                                val selectedDate =
                                    Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                if (selectedDate > state.endDate) {
                                    onEndDateClick(selectedDate)
                                }
                                onStartDateClick(selectedDate)
                            }
                            showStartDatePicker = false
                        }) {
                            Text(
                                text = stringResource(android.R.string.ok),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showStartDatePicker = false }) {
                            Text(
                                text = stringResource(android.R.string.cancel),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = DatePickerDefaults.colors().copy(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
                ) {
                    DatePicker(
                        colors =
                            DatePickerDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                                selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,
                                todayContentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                        state = startDatePickerState,
                        title = null,
                        headline = null,
                        showModeToggle = false,
                    )
                }
            }
            if (showEndDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showEndDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            endDatePickerState.selectedDateMillis?.let {
                                val selectedDate =
                                    Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                if (selectedDate < state.startDate) {
                                    onStartDateClick(selectedDate)
                                }
                                onEndDateClick(selectedDate)
                            }
                            showEndDatePicker = false
                        }) {
                            Text(
                                text = stringResource(android.R.string.ok),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showEndDatePicker = false }) {
                            Text(
                                text = stringResource(android.R.string.cancel),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = DatePickerDefaults.colors().copy(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                    )
                ) {
                    DatePicker(
                        colors = DatePickerDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                            selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,
                            todayContentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        state = endDatePickerState,
                        title = null,
                        headline = null,
                        showModeToggle = false,
                    )
                }
            }
        }
    }
}
