package com.tahadroid.thenews.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tahadroid.thenews.repository.NewsRepository

class SearchNewsViewModelFactory(
    val newsRepository: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchNewsViewModel(newsRepository) as T
    }
}