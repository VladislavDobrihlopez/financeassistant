package com.dobrihlopez.financeassistant.feature.transaction.core.model

import com.dobrihlopez.financeassistant.core.model.account.UserAccountDetailed
import com.dobrihlopez.financeassistant.core.model.category.Category
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
    val updatedAt: String,
)
