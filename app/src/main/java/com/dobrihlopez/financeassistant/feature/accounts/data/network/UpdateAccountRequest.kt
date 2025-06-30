package com.dobrihlopez.financeassistant.feature.accounts.data.network

import com.google.gson.annotations.SerializedName

data class UpdateAccountRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("balance")
    val balance: String,
    @SerializedName("currency")
    val currency: String,
)
