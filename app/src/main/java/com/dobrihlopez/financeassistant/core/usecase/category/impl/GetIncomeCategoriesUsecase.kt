package com.dobrihlopez.financeassistant.core.usecase.category.impl

import com.dobrihlopez.financeassistant.core.model.category.Category
import com.dobrihlopez.financeassistant.core.usecase.category.GetTypedCategoriesUsecase
import com.dobrihlopez.financeassistant.feature.categories.domain.CategoriesRepository
import javax.inject.Inject

class GetIncomeCategoriesUsecase @Inject constructor(
    private val repository: CategoriesRepository,
) : GetTypedCategoriesUsecase {
    override suspend operator fun invoke(): List<Category> {
        return repository.getIncomeCategories()
    }
}
