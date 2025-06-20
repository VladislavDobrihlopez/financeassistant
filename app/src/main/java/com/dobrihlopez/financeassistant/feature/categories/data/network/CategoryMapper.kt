package com.dobrihlopez.financeassistant.feature.categories.data.network

import com.dobrihlopez.financeassistant.feature.categories.domain.Category

fun CategoryDto.mapToDomain(): Category {
    return Category(
        emoji = emoji,
        id = id,
        isIncome = isIncome,
        name = name,
    )
}
