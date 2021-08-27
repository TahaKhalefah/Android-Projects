package com.tahadroid.thenews.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tahadroid.thenews.models.NewsResponse
import com.tahadroid.thenews.repository.NewsRepository
import com.tahadroid.thenews.utills.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchNewsViewModel(val newsRepository: NewsRepository) : ViewModel() {
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val page = 1
    fun getSearchNews(q: String) = viewModelScope.launch {
        searchNews.value = Resource.Loading()
        val response = newsRepository.getSearchNews(q, page)
        searchNews.value = handleSearchResponse(response)
    }

    private fun handleSearchResponse(response: Response<NewsResponse>): Resource<NewsResponse>? {
        if (response.isSuccessful) {
            response.body()?.let {searchData->
                return Resource.Success(searchData)
            }
        }
        return Resource.Error(response.message())
    }

}