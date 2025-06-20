package com.dobrihlopez.financeassistant.feature.categories.data.repo

import com.dobrihlopez.financeassistant.core.CoroutineDispatchers
import com.dobrihlopez.financeassistant.feature.categories.data.network.CategoryApi
import com.dobrihlopez.financeassistant.feature.categories.data.network.mapToDomain
import com.dobrihlopez.financeassistant.feature.categories.domain.CategoriesRepository
import com.dobrihlopez.financeassistant.feature.categories.domain.Category
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val api: CategoryApi,
    private val coroutineDispatchers: CoroutineDispatchers,
) : CategoriesRepository {
    override suspend fun getAllCategories(): List<Category> {
        return withContext(coroutineDispatchers.io) {
            api.getAllCategories().map { it.mapToDomain() }
        }
    }
}
