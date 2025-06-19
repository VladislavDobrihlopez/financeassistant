package com.dobrihlopez.financeassistant.feature.accounts.data.model

data class AccountDto(
    val id: Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String
) 