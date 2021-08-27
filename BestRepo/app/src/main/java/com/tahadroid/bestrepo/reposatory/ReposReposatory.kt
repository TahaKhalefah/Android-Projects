package com.tahadroid.bestrepo.reposatory

import com.tahadroid.bestrepo.network.GithubApiService
import javax.inject.Inject

class ReposReposatory
@Inject
constructor(val apiService: GithubApiService)
{
    suspend fun getRepos() = apiService.getRepos()
}