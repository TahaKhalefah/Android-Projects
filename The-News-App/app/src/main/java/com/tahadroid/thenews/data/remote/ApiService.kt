package com.tahadroid.thenews.data.remote

import com.tahadroid.thenews.models.NewsResponse
import com.tahadroid.thenews.utills.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //https://newsapi.org/v2/top-headlines?country=us&page=1&apiKey=ac231a0a018d4182aca5404ee67a2b0a
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        country: String = "us",
        @Query("page")
        page: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ):Response<NewsResponse>
  //  https://newsapi.org/v2/everything?q=egypt&page=1&apiKey=ac231a0a018d4182aca5404ee67a2b0a
    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        q: String,
        @Query("page")
        page: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ):Response<NewsResponse>

}