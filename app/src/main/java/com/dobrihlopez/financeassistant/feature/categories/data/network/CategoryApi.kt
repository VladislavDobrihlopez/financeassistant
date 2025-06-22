package com.dobrihlopez.financeassistant.feature.categories.data.network

import com.dobrihlopez.financeassistant.core.model.CategoryDto
import retrofit2.http.GET

interface CategoryApi {
    @GET("v1/categories")
    suspend fun getAllCategories(): List<CategoryDto>
}
