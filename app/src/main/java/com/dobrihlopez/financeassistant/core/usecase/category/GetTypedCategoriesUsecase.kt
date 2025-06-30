package com.dobrihlopez.financeassistant.core.usecase.category

import com.dobrihlopez.financeassistant.core.model.category.Category

interface GetTypedCategoriesUsecase {
    suspend operator fun invoke(): List<Category>
}
