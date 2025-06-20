package com.dobrihlopez.financeassistant.feature.transaction.core_data

import com.dobrihlopez.financeassistant.core.Transaction
import com.dobrihlopez.financeassistant.core.network.TransactionApi
import com.dobrihlopez.financeassistant.feature.transaction.core_data.transactions.toDomain
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