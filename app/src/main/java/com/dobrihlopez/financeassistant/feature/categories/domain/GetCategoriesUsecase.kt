package com.dobrihlopez.financeassistant.feature.categories.domain

import javax.inject.Inject

class GetCategoriesUsecase @Inject constructor(
    private val repository: CategoriesRepository,
) {
    suspend operator fun invoke(): List<Category> {
        return repository.getAllCategories()
    }
}