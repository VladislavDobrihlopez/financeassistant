package com.dobrihlopez.financeassistant.feature.accounts.presentation.screen

import androidx.annotation.StringRes
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class AccountActionItem
    @OptIn(ExperimentalUuidApi::class)
    constructor(
        val id: String = Uuid.random().toString(),
        @StringRes val title: Int,
        val currency: String,
        val value: String? = null,
        val emoji: String? = null,
        val onClick: () -> Unit,
    )
