package com.dobrihlopez.financeassistant.core.data.article

import com.dobrihlopez.financeassistant.core.CoroutineDispatchers
import com.dobrihlopez.financeassistant.core.domain.article.Article
import com.dobrihlopez.financeassistant.core.domain.article.ArticleRepository
import com.dobrihlopez.financeassistant.core.network.ArticleApi
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val api: ArticleApi,
    private val coroutineDispatchers: CoroutineDispatchers,
) : ArticleRepository {
    override suspend fun getArticleList(): List<Article> = withContext(coroutineDispatchers.io) {
        api.getArticleList().map { it.toDomain() }
    }
} 