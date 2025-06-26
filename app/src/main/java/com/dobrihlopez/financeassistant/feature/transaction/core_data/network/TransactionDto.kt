package com.dobrihlopez.financeassistant.feature.transaction.core_data.network

import com.dobrihlopez.financeassistant.core_remote.network.dto.AccountDto
import com.dobrihlopez.financeassistant.core_remote.network.dto.CategoryDto
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

data class TransactionDtoShorten(
    @SerializedName("id")
    val id: Int,
    @SerializedName("accountId")
    val accountId: Int,
    @SerializedName("categoryId")
    val categoryId: Int,
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
