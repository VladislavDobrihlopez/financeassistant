package com.dobrihlopez.financeassistant.feature.transaction.income.data

import com.dobrihlopez.financeassistant.core.CoroutineDispatchers
import com.dobrihlopez.financeassistant.core.network.IncomeApi
import com.dobrihlopez.financeassistant.feature.transaction.income.domain.Income
import com.dobrihlopez.financeassistant.feature.transaction.income.domain.IncomeHistory
import com.dobrihlopez.financeassistant.feature.transaction.income.domain.IncomeRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IncomeRepositoryImpl @Inject constructor(
    private val api: IncomeApi,
    private val coroutineDispatchers: CoroutineDispatchers,
) : IncomeRepository {
    override suspend fun getIncomeList(): List<Income> =
        withContext(coroutineDispatchers.io) {
            api.getIncomeList().map { it.toDomain() }
        }

    override suspend fun getIncomeHistory(period: String?): List<IncomeHistory> =
        withContext(coroutineDispatchers.io) {
            api.getIncomeHistory(period).map { it.toDomain() }
        }
} 