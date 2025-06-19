package com.dobrihlopez.financeassistant.feature.accounts.data.model

data class UpdateAccountRequest(
    val name: String,
    val balance: String,
    val currency: String
) 