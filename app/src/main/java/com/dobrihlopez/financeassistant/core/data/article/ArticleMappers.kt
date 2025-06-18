package com.dobrihlopez.financeassistant.core.data.article

import com.dobrihlopez.financeassistant.core.domain.article.Article
import com.dobrihlopez.financeassistant.core.network.ArticleDto

fun ArticleDto.toDomain(): Article = Article(
    id = id,
    name = name,
    type = type
) 