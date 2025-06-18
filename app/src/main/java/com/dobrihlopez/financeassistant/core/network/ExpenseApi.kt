package com.dobrihlopez.financeassistant.core.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ExpenseApi {
    @GET("expense/list")
    suspend fun getExpenseList(): List<ExpenseDto>

    @GET("expense/history")
    suspend fun getExpenseHistory(
        @Query("period") period: String? = null
    ): List<ExpenseHistoryDto>
}

data class ExpenseDto(
    val id: Long,
    val amount: Double,
    val category: String,
    val date: String
)

data class ExpenseHistoryDto(
    val id: Long,
    val amount: Double,
    val date: String
) 