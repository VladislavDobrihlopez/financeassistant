package com.dobrihlopez.financeassistant.core.domain.article

interface ArticleRepository {
    suspend fun getArticleList(): List<Article>
} 