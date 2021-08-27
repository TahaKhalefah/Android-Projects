package com.tahadroid.aflam4u.data.remote

import com.tahadroid.aflam4u.pojo.details.DetailsResponse
import com.tahadroid.aflam4u.pojo.popular.PopularResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //https://api.themoviedb.org/3/movie/popular?api_key=fe8a58e699f73e7973809e475762d756&language=en-US
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): Single<PopularResponse>

    //https://api.themoviedb.org/3/movie/18?api_key=fe8a58e699f73e7973809e475762d756&language=en-US
    @GET("movie/{movieId}")
    fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") api_key: String
    ): Single<DetailsResponse>

}