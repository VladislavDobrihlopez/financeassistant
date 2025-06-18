package com.dobrihlopez.financeassistant.core.data.article

import com.dobrihlopez.financeassistant.core.domain.article.Article
import com.dobrihlopez.financeassistant.core.domain.article.ArticleRepository
import com.dobrihlopez.financeassistant.core.network.ArticleApi
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val api: ArticleApi
) : ArticleRepository {
    override suspend fun getArticleList(): List<Article> =
        api.getArticleList().map { it.toDomain() }
} 