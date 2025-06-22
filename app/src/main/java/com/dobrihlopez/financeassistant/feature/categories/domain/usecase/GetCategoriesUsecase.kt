package com.dobrihlopez.financeassistant.feature.categories.domain.usecase

import com.dobrihlopez.financeassistant.feature.categories.domain.model.Category

class GetCategoriesUsecase
    @javax.inject.Inject
    constructor(
        private val repository: com.dobrihlopez.financeassistant.feature.categories.domain.CategoriesRepository,
    ) {
        suspend operator fun invoke(): List<Category> {
            return repository.getAllCategories()
        }
    }
