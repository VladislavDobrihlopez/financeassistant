package com.dobrihlopez.financeassistant.core

import com.dobrihlopez.financeassistant.feature.accounts.domain.UserAccountDetailed
import com.dobrihlopez.financeassistant.feature.categories.domain.Category

data class Transaction(
    val account: UserAccountDetailed,
    val amount: String,
    val category: Category,
    val comment: String,
    val createdAt: String,
    val id: Int,
    val transactionDate: String,
    val updatedAt: String
)
