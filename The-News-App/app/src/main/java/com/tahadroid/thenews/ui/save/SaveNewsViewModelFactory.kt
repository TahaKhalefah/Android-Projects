package com.tahadroid.thenews.ui.save

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tahadroid.thenews.repository.NewsRepository

class SaveNewsViewModelFactory(
    val repo:NewsRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SaveNewsViewModel(repo) as T
    }
}