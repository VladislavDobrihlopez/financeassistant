package com.dobrihlopez.financeassistant.feature.transaction.core

import com.dobrihlopez.financeassistant.core.Transaction
import com.dobrihlopez.financeassistant.feature.transaction.core_data.TransactionRepositoryImpl
import javax.inject.Inject

class GetTransactionsForPeriodUseCase @Inject constructor(
    private val repository: TransactionRepositoryImpl
) {
    suspend operator fun invoke(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<Transaction> = repository.getTransactionsForPeriod(accountId, startDate, endDate)
}