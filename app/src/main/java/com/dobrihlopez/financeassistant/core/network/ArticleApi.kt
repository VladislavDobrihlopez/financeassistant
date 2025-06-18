package com.dobrihlopez.financeassistant.core.network

import retrofit2.http.GET

interface ArticleApi {
    @GET("article/list")
    suspend fun getArticleList(): List<ArticleDto>
}

data class ArticleDto(
    val id: Long,
    val name: String,
    val type: String
) 