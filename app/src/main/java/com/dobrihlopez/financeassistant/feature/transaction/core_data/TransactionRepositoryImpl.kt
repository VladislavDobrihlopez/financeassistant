package com.dobrihlopez.financeassistant.feature.transaction.core_data

import com.dobrihlopez.financeassistant.core.CoroutineDispatchers
import com.dobrihlopez.financeassistant.core.Transaction
import com.dobrihlopez.financeassistant.core.network.TransactionApi
import com.dobrihlopez.financeassistant.feature.transaction.core_data.transactions.toDomain
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val api: TransactionApi,
    private val coroutineDispatchers: CoroutineDispatchers,
) {
    suspend fun getTransactionsForPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<Transaction> {
        return withContext(coroutineDispatchers.io) {
            api.getTransactionsForPeriod(accountId, startDate, endDate)
                .map { it.toDomain() }
        }
    }
}