package com.dobrihlopez.financeassistant.core.model.category

@kotlinx.serialization.Serializable
data class Category(
    val emoji: String? = null,
    val id: Int,
    val isIncome: Boolean,
    val name: String,
)