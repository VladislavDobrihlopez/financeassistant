package com.dobrihlopez.financeassistant.feature.categories.data.mapper

import com.dobrihlopez.financeassistant.core.model.category.Category
import com.dobrihlopez.financeassistant.core_remote.network.dto.CategoryDto

fun CategoryDto.toDomain(): Category {
    return Category(
        emoji = emoji,
        id = id,
        isIncome = isIncome,
        name = name,
    )
}
