package com.dobrihlopez.financeassistant.feature.categories.domain

interface CategoriesRepository {
    suspend fun getAllCategories(): List<Category>
}
