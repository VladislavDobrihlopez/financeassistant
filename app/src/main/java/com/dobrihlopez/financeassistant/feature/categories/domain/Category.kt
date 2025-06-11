package com.dobrihlopez.financeassistant.feature.categories.domain

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val emoji: String? = null,
    val id: Int,
    val isIncome: Boolean,
    val name: String
)
