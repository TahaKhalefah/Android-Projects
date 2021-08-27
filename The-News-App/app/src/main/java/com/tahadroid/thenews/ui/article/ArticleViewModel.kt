package com.tahadroid.thenews.ui.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tahadroid.thenews.models.Article
import com.tahadroid.thenews.repository.NewsRepository
import kotlinx.coroutines.launch

class ArticleViewModel(val repo:NewsRepository) : ViewModel() {
    fun addArticle(article: Article) =viewModelScope.launch {
        repo.addArticle(article)
    }
}