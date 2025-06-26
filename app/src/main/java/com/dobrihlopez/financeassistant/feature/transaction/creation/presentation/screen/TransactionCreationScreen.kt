package com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dobrihlopez.financeassistant.R
import com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.TransactionCreationComponent
import com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.TransactionCreationStore

@Composable
fun CreationScreen(component: TransactionCreationComponent, paddingValues: PaddingValues) {
    val state = component.state.collectAsStateWithLifecycle().value

    CreationContent(
        state,
        paddingValues,
        onDeleteTransaction = component::deleteTransaction,
        onBalanceChanged = component::updateSum,
        onDateChanged = component::updateDate,
        onTimeChanged = component::updateTime,
        onCommentaryChanged = component::updateComment,
        onCategoryChanged = component::updateCategory,
    )

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        component.labels.collect { label ->
            when (label) {
                TransactionCreationStore.Label.ChangesSuccessfullyApplied -> {
                    Toast.makeText(context,
                        context.getString(R.string.transaction_result_changes_applied), Toast.LENGTH_SHORT).show()
                }
                TransactionCreationStore.Label.SuccessfullyCreated -> {
                    Toast.makeText(context,
                        context.getString(R.string.transaction_result_created), Toast.LENGTH_SHORT).show()
                }
                TransactionCreationStore.Label.SuccessfullyDeleted -> {
                    Toast.makeText(context,
                        context.getString(R.string.transaction_result_deleted), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
