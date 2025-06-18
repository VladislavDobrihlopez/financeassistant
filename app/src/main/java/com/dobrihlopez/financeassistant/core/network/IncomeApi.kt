package com.dobrihlopez.financeassistant.core.network

import retrofit2.http.GET
import retrofit2.http.Query

interface IncomeApi {
    @GET("income/list")
    suspend fun getIncomeList(): List<IncomeDto>

    @GET("income/history")
    suspend fun getIncomeHistory(
        @Query("period") period: String? = null
    ): List<IncomeHistoryDto>
}

data class IncomeDto(
    val id: Long,
    val amount: Double,
    val category: String,
    val date: String
)

data class IncomeHistoryDto(
    val id: Long,
    val amount: Double,
    val date: String
) 