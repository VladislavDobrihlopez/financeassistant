package com.dobrihlopez.financeassistant.feature.transaction.core_data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionApi {
    @GET("v1/transactions/account/{accountId}/period")
    suspend fun getTransactionsForPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?,
    ): List<TransactionDto>
}
