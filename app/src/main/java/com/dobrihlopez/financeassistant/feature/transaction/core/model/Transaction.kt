package com.dobrihlopez.financeassistant.feature.transaction.core.model

import com.dobrihlopez.financeassistant.feature.accounts.domain.model.UserAccountDetailed
import com.dobrihlopez.financeassistant.feature.categories.domain.model.Category
import kotlinx.serialization.Serializable

@Serializable
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
