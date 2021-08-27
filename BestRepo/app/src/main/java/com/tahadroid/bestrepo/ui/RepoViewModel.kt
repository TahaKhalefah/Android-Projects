package com.tahadroid.bestrepo.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tahadroid.bestrepo.model.GitHubResponse
import com.tahadroid.bestrepo.reposatory.ReposReposatory
import com.tahadroid.bestrepo.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class RepoViewModel
@ViewModelInject
constructor(val reposatory: ReposReposatory) : ViewModel() {

    private val _repoList: MutableLiveData<Resource<GitHubResponse>> = MutableLiveData()
    private var page = 1
    var gitHubResponse: GitHubResponse? = null

     fun getRepos() : LiveData<Resource<GitHubResponse>> {
         viewModelScope.launch(Dispatchers.IO) {
             _repoList.postValue( Resource.Loading())
             _repoList.postValue(handleResponse(reposatory.getRepos()))
         }
         return _repoList
     }

    private fun handleResponse(response: Response<GitHubResponse>): Resource<GitHubResponse> {
        if (response.isSuccessful) {
            response.body()?.let { data ->
                page++
                if (gitHubResponse == null) {
                    gitHubResponse = data
                } else {
                    val old = gitHubResponse?.items
                    val new = data.items
                    old?.addAll(new)
                }
                return Resource.Success(gitHubResponse?: data)
            }
        }
        return Resource.Error(response.message())
    }

}