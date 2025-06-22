package com.dobrihlopez.financeassistant.feature.categories.data.mapper

import com.dobrihlopez.financeassistant.core.model.CategoryDto
import com.dobrihlopez.financeassistant.feature.categories.domain.model.Category

fun CategoryDto.toDomain(): Category {
    return Category(
        emoji = emoji,
        id = id,
        isIncome = isIncome,
        name = name,
    )
}
