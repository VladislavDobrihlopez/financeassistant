package com.dobrihlopez.financeassistant.core.domain.income

interface IncomeRepository {
    suspend fun getIncomeList(): List<Income>
    suspend fun getIncomeHistory(period: String? = null): List<IncomeHistory>
} 