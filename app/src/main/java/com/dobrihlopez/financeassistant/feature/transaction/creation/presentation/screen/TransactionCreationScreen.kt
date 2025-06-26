package com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dobrihlopez.financeassistant.feature.transaction.creation.presentation.TransactionCreationComponent

@Composable
fun CreationScreen(component: TransactionCreationComponent, paddingValues: PaddingValues) {
    val state = component.state.collectAsStateWithLifecycle().value

    CreationContent(
        state,
        paddingValues,
        onDeleteTransaction = {},
        onBalanceChanged = component::updateSum,
        onDateChanged = component::updateDate,
        onTimeChanged = component::updateTime,
        onCommentaryChanged = component::updateComment,
        onCategoryChanged = component::updateCategory,
    )
}
