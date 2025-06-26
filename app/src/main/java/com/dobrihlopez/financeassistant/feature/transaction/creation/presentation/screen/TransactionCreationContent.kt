package com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.screen

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.core.toDateFormat
import com.dobrihlopez.financeassistant.core.toTimeFormat
import com.dobrihlopez.financeassistant.coreui.composable.ErrorSnackbarHost
import com.dobrihlopez.financeassistant.coreui.composable.LoadingProgressBar
import com.dobrihlopez.financeassistant.coreui.ui.theme.spacing
import com.dobrihlopez.financeassistant.feature.accounts.presentation.composable.BalanceEditDialog
import com.dobrihlopez.financeassistant.feature.transaction.core_ui.composable.modalsheet.CommentaryInputBottomSheet
import com.dobrihlopez.financeassistant.feature.transaction.core_ui.composable.picker.FinanceDatePickerDialog
import com.dobrihlopez.financeassistant.feature.transaction.core_ui.composable.picker.FinanceTimePickerDialog
import com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.TransactionCreationStore
import com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.composable.DeletionButton
import com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.composable.TransactionOptionItem
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset

private enum class Option(@StringRes val itemName: Int, val hasDropDownSelection: Boolean) {
    ACCOUNT(R.string.transaction_account, true),
    CATEGORY(R.string.transaction_category, true),
    SUM(R.string.transaction_sum, false),
    DATE(R.string.transaction_date, false),
    TIME(R.string.transaction_time, false),
    COMMENTARY(R.string.empty, false),
}

private fun Option.getValue(state: TransactionCreationStore.State.Success): String {
    return when (this) {
        Option.ACCOUNT -> state.originalTransaction?.account?.name ?: "Default"
        Option.CATEGORY -> state.chosenCategory?.name ?: state.originalTransaction?.category?.name
        ?: state.categories.firstOrNull()?.name ?: ""
        Option.SUM -> state.sum
        Option.DATE -> (state.date?.toLocalDate() ?: LocalDate.now()).toDateFormat()
        Option.TIME -> (state.date?.toLocalTime() ?: LocalTime.now()).toTimeFormat()
        Option.COMMENTARY -> state.comment
    }.toString()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreationContent(
    state: TransactionCreationStore.State,
    paddingValues: PaddingValues,
    onDeleteTransaction: () -> Unit,
    onBalanceChanged: (String) -> Unit,
    onDateChanged: (LocalDate) -> Unit,
    onTimeChanged: (LocalTime) -> Unit,
    onCommentaryChanged: (String) -> Unit,
) {
    Scaffold(snackbarHost = {
        if (state is TransactionCreationStore.State.Failed) {
            ErrorSnackbarHost(
                errorResId = null,
            )
        }
    }) { innerPadding ->
        when (state) {
            TransactionCreationStore.State.Failed -> {}
            TransactionCreationStore.State.Loading -> LoadingProgressBar()
            is TransactionCreationStore.State.Success -> {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .padding(innerPadding)
                ) {
                    var showBalanceSheet by rememberSaveable { mutableStateOf(false) }
                    val balanceSheetState = rememberModalBottomSheetState()
                    val commentarySheetState = rememberModalBottomSheetState()

                    var showDatePicker by rememberSaveable { mutableStateOf(false) }
                    var showTimePicker by rememberSaveable { mutableStateOf(false) }
                    var showCommentarySheet by rememberSaveable { mutableStateOf(false) }

                    val datePickerState = rememberDatePickerState(
                        initialSelectedDateMillis = (state.date?.toLocalDate() ?: LocalDate.now())
                            .atStartOfDay(ZoneOffset.UTC)
                            .toInstant()
                            .toEpochMilli()
                    )

                    val timePickerState = rememberTimePickerState(
                        initialHour = (state.date?.toLocalTime() ?: LocalTime.now()).hour,
                        initialMinute = (state.date?.toLocalTime() ?: LocalTime.now()).minute,
                    )

                    LazyColumn {
                        items(items = Option.entries, key = { it.name }) { option ->
                            TransactionOptionItem(
                                content = stringResource(option.itemName),
                                onClick = {
                                    when (option) {
                                        Option.ACCOUNT -> {}
                                        Option.CATEGORY -> {}
                                        Option.SUM -> showBalanceSheet = true
                                        Option.DATE -> showDatePicker = true
                                        Option.TIME -> showTimePicker = true
                                        Option.COMMENTARY -> showCommentarySheet = true
                                    }
                                },
                                value = option.getValue(state),
                                hasChooseOption = option.hasDropDownSelection
                            )
                            HorizontalDivider()
                        }
                        item {
                            AnimatedVisibility(state.mode == TransactionCreationStore.LaunchMode.EDITING) {
                                DeletionButton(
                                    modifier = Modifier
                                        .padding(
                                            horizontal = MaterialTheme.spacing.medium,
                                            vertical = MaterialTheme.spacing.extraLarge
                                        )
                                        .fillMaxWidth(),
                                    onClick = onDeleteTransaction,
                                    content = stringResource(
                                        R.string.transaction_operation
                                    )
                                )
                            }
                        }
                    }

                    if (showBalanceSheet) {
                        BalanceEditDialog(
                            sheetState = balanceSheetState,
                            initialBalance = state.sum,
                            onDismiss = {
                                showBalanceSheet = false
                            },
                            onDone = { newBalance ->
                                showBalanceSheet = false
                                onBalanceChanged(newBalance)
                            },
                        )
                    }

                    if (showDatePicker) {
                        FinanceDatePickerDialog(
                            datePickerState = datePickerState,
                            onDismiss = { showDatePicker = false },
                            onConfirm = { selectedDate ->
                                showDatePicker = false
                                onDateChanged(selectedDate)
                            }
                        )
                    }

                    if (showTimePicker) {
                        FinanceTimePickerDialog(
                            timePickerState = timePickerState,
                            onDismiss = { showTimePicker = false },
                            onConfirm = { selectedTime ->
                                showTimePicker = false
                                onTimeChanged(selectedTime)
                            }
                        )
                    }


                    if (showCommentarySheet) {
                        CommentaryInputBottomSheet(
                            initialText = state.comment,
                            sheetState = commentarySheetState,
                            onDismiss = { showCommentarySheet = false },
                            onDone = { comment ->
                                showCommentarySheet = false
                                onCommentaryChanged(comment)
                            }
                        )
                    }
                }
            }
        }
    }
}
