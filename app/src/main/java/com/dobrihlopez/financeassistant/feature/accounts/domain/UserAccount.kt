package com.dobrihlopez.financeassistant.feature.accounts.domain

import com.dobrihlopez.financeassistant.feature.accounts.data.network.AccountDto

data class UserAccount(
    val balance: String,
    val createdAt: String,
    val currency: String,
    val id: Int,
    val name: String,
    val updatedAt: String,
    val userId: Int,
)

fun AccountDto.toDomainAccount(): UserAccount =
    UserAccount(
        id = id,
        name = name,
        balance = balance,
        currency = currency,
        createdAt = createdAt,
        updatedAt = updatedAt,
        userId = userId
    )