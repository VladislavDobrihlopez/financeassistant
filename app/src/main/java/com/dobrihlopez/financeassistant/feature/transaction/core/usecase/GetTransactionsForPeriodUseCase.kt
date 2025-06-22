package com.dobrihlopez.financeassistant.feature.transaction.core.usecase

import com.dobrihlopez.financeassistant.feature.transaction.core.model.Transaction
import com.dobrihlopez.financeassistant.feature.transaction.core_data.TransactionRepositoryImpl

class GetTransactionsForPeriodUseCase @javax.inject.Inject constructor(
    private val repository: TransactionRepositoryImpl
) {
    suspend operator fun invoke(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<Transaction> = repository.getTransactionsForPeriod(accountId, startDate, endDate)
}