package com.dobrihlopez.financeassistant.core.usecase.category

import com.dobrihlopez.financeassistant.core.model.category.Category
import com.dobrihlopez.financeassistant.feature.categories.domain.CategoriesRepository
import javax.inject.Inject

class GetAllCategoriesUseCase
@Inject
constructor(
    private val repository: CategoriesRepository,
) {
    suspend operator fun invoke(): List<Category> {
        return repository.getAllCategories()
    }
}
