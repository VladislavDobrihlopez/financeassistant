package com.dobrihlopez.financeassistant.feature.accounts.data.network

import com.dobrihlopez.financeassistant.core_remote.network.dto.AccountDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountsApiService {
    @GET(PATH)
    suspend fun getAccounts(): List<AccountDto>

    @PUT("$PATH/{id}")
    suspend fun updateAccount(
        @Path("id") id: Int,
        @Body body: UpdateAccountRequest,
    ): AccountDto

    companion object {
        protected const val PATH = "v1/accounts"
    }
}
