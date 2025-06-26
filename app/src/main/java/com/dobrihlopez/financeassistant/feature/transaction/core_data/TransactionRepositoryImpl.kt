package com.dobrihlopez.financeassistant.feature.transaction.core_data

import com.dobrihlopez.financeassistant.core.CoroutineDispatchers
import com.dobrihlopez.financeassistant.core_remote.network.retryWithDelay
import com.dobrihlopez.financeassistant.feature.transaction.core.model.Transaction
import com.dobrihlopez.financeassistant.feature.transaction.core_data.mapper.toDomain
import com.dobrihlopez.financeassistant.feature.transaction.core_data.network.TransactionApi
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val api: TransactionApi,
    private val coroutineDispatchers: CoroutineDispatchers,
) {
    suspend fun getTransactionsForPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?,
    ): List<Transaction> {
        return withContext(coroutineDispatchers.io) {
            retryWithDelay {
                api.getTransactionsForPeriod(accountId, startDate, endDate)
            }.map { it.toDomain() }
        }
    }
}
