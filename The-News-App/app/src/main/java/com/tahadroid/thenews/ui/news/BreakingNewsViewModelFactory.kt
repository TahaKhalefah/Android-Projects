package com.tahadroid.thenews.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tahadroid.thenews.repository.NewsRepository

class BreakingNewsViewModelFactory(
    val newsRepository: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BreakingNewsViewModel(newsRepository) as T
    }
}