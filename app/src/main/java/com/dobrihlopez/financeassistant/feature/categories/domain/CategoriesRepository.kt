package com.dobrihlopez.financeassistant.feature.categories.domain

import com.dobrihlopez.financeassistant.feature.categories.domain.model.Category

interface CategoriesRepository {
    suspend fun getAllCategories(): List<Category>
}
