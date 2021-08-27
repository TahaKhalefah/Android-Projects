package com.tahadroid.bestrepo.network

import com.tahadroid.bestrepo.model.GitHubResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {
    //https://api.github.com/search/repositories?q=created:%3E2017-10-22&sort=stars&order=desc/
    @GET("search/repositories?q=created:%3E2017-10-22")
    suspend fun getRepos(
        @Query("sort")
        sort : String = "stars",
        @Query("order")
        order : String = "desc",
        @Query("page")
        page : Int = 1
    ):Response<GitHubResponse>
}