package com.dobrihlopez.financeassistant.core

import android.accounts.Account
import com.dobrihlopez.financeassistant.feature.categories.domain.Category

data class Transaction(
    val account: Account,
    val amount: String,
    val category: Category,
    val comment: String,
    val createdAt: String,
    val id: Int,
    val transactionDate: String,
    val updatedAt: String
)
