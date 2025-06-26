package com.dobrihlopez.financeassistant.feature.transaction.creation.domain

import com.dobrihlopez.financeassistant.feature.transaction.core.model.Transaction
import java.time.LocalDateTime

interface TransactionRepository {
    suspend fun deleteTransaction(transactionId: Int)
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: LocalDateTime,
        comment: String,
    )
}
