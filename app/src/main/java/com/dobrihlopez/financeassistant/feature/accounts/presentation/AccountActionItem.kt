package com.dobrihlopez.financeassistant.feature.accounts.presentation

import androidx.annotation.StringRes
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class AccountActionItem @OptIn(ExperimentalUuidApi::class) constructor(
    val id: String = Uuid.random().toString(),
    @StringRes val title: Int,
    val value: String,
    val currency: String,
    val emoji: String? = null,
    val onClick: () -> Unit,
)
