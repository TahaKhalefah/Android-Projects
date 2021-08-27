package com.tahadroid.aflam4u.repository

import com.tahadroid.aflam4u.data.remote.ApiClient
import com.tahadroid.aflam4u.utils.API_KEY

class MovieRepository {
    fun getPopular()= ApiClient.apiService.getPopularMovies(API_KEY)

    fun getDetails(movieId:Int) = ApiClient.apiService.getMovieDetails(movieId,API_KEY)
}