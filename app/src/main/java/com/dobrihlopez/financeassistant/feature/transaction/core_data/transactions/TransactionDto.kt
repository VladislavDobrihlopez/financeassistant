package com.dobrihlopez.financeassistant.feature.transaction.core_data.transactions

import kotlinx.serialization.Serializable

@Serializable
data class TransactionDto(
    val id: Int,
    val account: AccountDto,
    val category: CategoryDto,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
)

@Serializable
data class AccountDto(
    val id: Int,
    val name: String?,
    val balance: String?,
    val currency: String?
)

@Serializable
data class CategoryDto(
    val id: Int,
    val name: String?,
    val emoji: String?,
    val isIncome: Boolean
) 