package com.dobrihlopez.financeassistant.feature.transaction.core

class GetTransactionsForPeriodUseCase @javax.inject.Inject constructor(
    private val repository: com.dobrihlopez.financeassistant.core.data.transactions.TransactionRepositoryImpl
) {
    suspend operator fun invoke(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<com.dobrihlopez.financeassistant.core.Transaction> = repository.getTransactionsForPeriod(accountId, startDate, endDate)
}