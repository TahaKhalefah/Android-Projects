package com.tahadroid.thenews.ui.save

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tahadroid.thenews.models.Article
import com.tahadroid.thenews.repository.NewsRepository
import kotlinx.coroutines.launch

class SaveNewsViewModel(val repo :NewsRepository) : ViewModel() {

fun getAllArticles() =repo.getAllArticles()
     fun deleteArticle(article: Article)=viewModelScope.launch {
        repo.deleteArticle(article)

    }
    fun addArticle(article: Article) =viewModelScope.launch {
        repo.addArticle(article)
    }
}