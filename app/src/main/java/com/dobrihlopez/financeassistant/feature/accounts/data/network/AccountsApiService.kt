package com.dobrihlopez.financeassistant.feature.accounts.data.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountsApiService {
    @GET("v1/accounts")
    suspend fun getAccounts(): List<AccountDto>

    @PUT("v1/accounts/{id}")
    suspend fun updateAccount(
        @Path("id") id: Int,
        @Body body: UpdateAccountRequest
    ): AccountDto
}