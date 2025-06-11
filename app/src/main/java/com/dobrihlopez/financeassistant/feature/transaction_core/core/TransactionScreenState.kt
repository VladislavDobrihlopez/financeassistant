package com.dobrihlopez.financeassistant.feature.transaction_core.core

import androidx.annotation.StringRes
import com.dobrihlopez.financeassistant.core.Transaction

sealed class TransactionScreenState {
    data object Loading : TransactionScreenState()
    data class Failed(@StringRes val resId: Int? = null) : TransactionScreenState()
    data class Succeeded(
        val transactions: List<Transaction>,
        val summaryText: String,
        val summaryValue: String
    ) : TransactionScreenState()
}