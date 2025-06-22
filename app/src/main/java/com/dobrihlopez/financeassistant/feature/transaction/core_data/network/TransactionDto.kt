package com.dobrihlopez.financeassistant.feature.transaction.core_data.network

import com.dobrihlopez.financeassistant.core.model.AccountDto
import com.dobrihlopez.financeassistant.core.model.CategoryDto
import com.google.gson.annotations.SerializedName

data class TransactionDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("account")
    val account: AccountDto,
    @SerializedName("category")
    val category: CategoryDto,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("transactionDate")
    val transactionDate: String,
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
)
