package com.dobrihlopez.financeassistant.feature.accounts.domain

data class UserAccount(
    val balance: String,
    val createdAt: String,
    val currency: String,
    val id: Int,
    val name: String,
    val updatedAt: String,
    val userId: Int,
)