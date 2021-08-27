package com.tahadroid.thenews.repository

import com.tahadroid.thenews.data.local.ArticleDatabase
import com.tahadroid.thenews.data.remote.ApiClient
import com.tahadroid.thenews.models.Article

class NewsRepository(val db: ArticleDatabase) {
    suspend fun getBreakingNews(country: String, page: Int) =
        ApiClient.api.getBreakingNews(country, page)

    suspend fun getSearchNews(q: String, page: Int) =
        ApiClient.api.searchForNews(q, page)

    suspend fun addArticle(article: Article) = db.getArticleDoa().upsert(article)

    fun getAllArticles() =db.getArticleDoa().getAllArticles()

    suspend fun deleteArticle(article:Article) =db.getArticleDoa().deleteArticle(article)
}