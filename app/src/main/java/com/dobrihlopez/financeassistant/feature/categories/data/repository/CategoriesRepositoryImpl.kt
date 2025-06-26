package com.dobrihlopez.financeassistant.feature.categories.data.repository

import com.dobrihlopez.financeassistant.core.CoroutineDispatchers
import com.dobrihlopez.financeassistant.core.model.category.Category
import com.dobrihlopez.financeassistant.core_remote.network.retryWithDelay
import com.dobrihlopez.financeassistant.feature.categories.data.mapper.toDomain
import com.dobrihlopez.financeassistant.feature.categories.data.network.CategoryApi
import com.dobrihlopez.financeassistant.feature.categories.domain.CategoriesRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val api: CategoryApi,
    private val coroutineDispatchers: CoroutineDispatchers,
) : CategoriesRepository {
    override suspend fun getAllCategories(): List<Category> {
        return withContext(coroutineDispatchers.io) {
            retryWithDelay {
                api.getAllCategories().map { it.toDomain() }
            }
        }
    }

    override suspend fun getIncomeCategories(): List<Category> {
        return withContext(coroutineDispatchers.io) {
            retryWithDelay {
                api.getFilteredCategories(typeIsIncome = true).map { it.toDomain() }
            }
        }
    }

    override suspend fun getExpenseCategories(): List<Category> {
        return withContext(coroutineDispatchers.io) {
            retryWithDelay {
                api.getFilteredCategories(typeIsIncome = false).map { it.toDomain() }
            }
        }
    }
}
