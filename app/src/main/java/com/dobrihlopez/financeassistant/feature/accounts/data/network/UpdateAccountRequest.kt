package com.dobrihlopez.financeassistant.feature.accounts.data.network

data class UpdateAccountRequest(
    val name: String,
    val balance: String,
    val currency: String
) 