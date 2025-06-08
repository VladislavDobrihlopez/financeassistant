package com.dobrihlopez.financeassistant.core.useraccount

data class UserAccount(
    val balance: String,
    val createdAt: String,
    val currency: String,
    val id: Int,
    val name: String,
    val updatedAt: String,
    val userId: Int,
)