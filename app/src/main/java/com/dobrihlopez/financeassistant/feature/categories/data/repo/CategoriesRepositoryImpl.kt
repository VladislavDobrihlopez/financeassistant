package com.dobrihlopez.financeassistant.feature.categories.data.repo

import com.dobrihlopez.financeassistant.feature.categories.data.network.CategoryApi
import com.dobrihlopez.financeassistant.feature.categories.data.network.mapToDomain
import com.dobrihlopez.financeassistant.feature.categories.domain.CategoriesRepository
import com.dobrihlopez.financeassistant.feature.categories.domain.Category
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val api: CategoryApi,
) : CategoriesRepository {
    override suspend fun getAllCategories(): List<Category> {
        return api.getAllCategories().map { it.mapToDomain() }
    }
}
