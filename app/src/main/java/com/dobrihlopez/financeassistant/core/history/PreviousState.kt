package com.dobrihlopez.financeassistant.core.history

data class PreviousState(
    val balance: String,
    val currency: String,
    val id: Int,
    val name: String
)