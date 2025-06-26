package com.dobrihlopez.financeassistant.feature.categories.domain

import com.dobrihlopez.financeassistant.core.model.category.Category

interface CategoriesRepository {
    suspend fun getAllCategories(): List<Category>
}
