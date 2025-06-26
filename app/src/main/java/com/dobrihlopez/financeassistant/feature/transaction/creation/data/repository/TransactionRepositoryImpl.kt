package com.dobrihlopez.financeassistant.feature.transaction.creation.data.repository

import com.dobrihlopez.financeassistant.core_remote.network.retryWithDelay
import com.dobrihlopez.financeassistant.feature.transaction.core.model.Transaction
import com.dobrihlopez.financeassistant.feature.transaction.creation.data.mapper.toRequest
import com.dobrihlopez.financeassistant.feature.transaction.creation.data.network.TransactionCrudApi
import com.dobrihlopez.financeassistant.feature.transaction.creation.data.network.TransactionRequest
import com.dobrihlopez.financeassistant.feature.transaction.creation.domain.TransactionRepository
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val apiService: TransactionCrudApi,
): TransactionRepository {
    override suspend fun deleteTransaction(transactionId: Int) {
        retryWithDelay {
            apiService.delete(transactionId)
        }
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        retryWithDelay {
            apiService.update(transaction.id, transaction.toRequest())
        }
    }

    override suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: LocalDateTime,
        comment: String,
    ) {
        retryWithDelay {
            apiService.create(
                TransactionRequest(
                    accountId = accountId,
                    categoryId = categoryId,
                    amount = amount,
                    comment = comment,
                    transactionDate = OffsetDateTime.of(transactionDate, ZoneOffset.UTC).toString()
                )
            )
        }
    }
}
