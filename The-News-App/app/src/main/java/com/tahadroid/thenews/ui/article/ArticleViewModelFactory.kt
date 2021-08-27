package com.tahadroid.thenews.ui.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tahadroid.thenews.repository.NewsRepository

class ArticleViewModelFactory(
    val repo: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleViewModel(repo) as T
    }
}