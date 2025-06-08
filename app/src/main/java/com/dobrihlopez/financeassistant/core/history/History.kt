package com.dobrihlopez.financeassistant.core.history

data class History(
    val accountId: Int,
    val changeTimestamp: String,
    val changeType: String,
    val createdAt: String,
    val id: Int,
    val newState: NewState,
    val previousState: PreviousState
)