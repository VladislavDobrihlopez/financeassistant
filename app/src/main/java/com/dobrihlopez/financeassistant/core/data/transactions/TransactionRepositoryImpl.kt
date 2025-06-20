package com.dobrihlopez.financeassistant.core.data.transactions

import com.dobrihlopez.financeassistant.core.Transaction
import com.dobrihlopez.financeassistant.core.network.TransactionApi
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val api: TransactionApi
) {
    suspend fun getTransactionsForPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<Transaction> {
        return api.getTransactionsForPeriod(accountId, startDate, endDate)
            .map { it.toDomain() }
    }
} 