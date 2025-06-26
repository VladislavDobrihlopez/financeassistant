package com.dobrihlopez.financeassistant.feature.transaction.creation.data.network

import com.dobrihlopez.financeassistant.feature.transaction.core_data.network.TransactionDto
import com.dobrihlopez.financeassistant.feature.transaction.core_data.network.TransactionDtoShorten
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TransactionCrudApi {
    @PUT("v1/transactions/{id}")
    suspend fun update(
        @Path("id") id: Int,
        @Body request: TransactionRequest,
    ): TransactionDto

    @POST("v1/transactions")
    suspend fun create(
        @Body request: TransactionRequest,
    ): TransactionDtoShorten

    @DELETE("v1/transactions/{id}")
    suspend fun delete(
        @Path("id") id: Int,
    )
}
