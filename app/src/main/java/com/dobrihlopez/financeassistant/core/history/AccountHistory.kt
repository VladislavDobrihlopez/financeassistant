package com.dobrihlopez.financeassistant.core.history

data class AccountHistory(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<History>
)