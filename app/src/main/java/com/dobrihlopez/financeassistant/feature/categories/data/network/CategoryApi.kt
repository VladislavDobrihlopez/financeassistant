package com.dobrihlopez.financeassistant.feature.categories.data.network

import com.dobrihlopez.financeassistant.core_remote.network.dto.CategoryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApi {
    @GET("v1/categories")
    suspend fun getAllCategories(): List<CategoryDto>

    @GET("v1/categories/type/{isIncome}")
    suspend fun getFilteredCategories(
        @Path("isIncome") typeIsIncome: Boolean,
    ): List<CategoryDto>
}
