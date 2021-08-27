package com.tahadroid.thenews.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tahadroid.thenews.models.Article
import com.tahadroid.thenews.models.NewsResponse
import com.tahadroid.thenews.repository.NewsRepository
import com.tahadroid.thenews.utills.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


class BreakingNewsViewModel(val newsRepository: NewsRepository) : ViewModel() {
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var page = 1
    var breakingNewsResponse: NewsResponse? = null

    init {
        getBreakingNews(country = "us")
    }

    fun getBreakingNews(country: String) = viewModelScope.launch {
        breakingNews.value = Resource.Loading()
        val response = newsRepository.getBreakingNews(country, page)
        breakingNews.value = handleResponse(response)
    }

    private fun handleResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { data ->
                page++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = data
                } else {
                    val old = breakingNewsResponse?.articles
                    val new = data.articles
                    old?.addAll(new)
                }
                return Resource.Success(breakingNewsResponse ?: data)
            }
        }
        return Resource.Error(response.message())
    }

}