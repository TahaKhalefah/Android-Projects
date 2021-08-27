package com.tahadroid.wing.ui.fragments.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tahadroid.wing.repository.Repository

class BasketViewModelFactory(val repo: Repository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BasketViewModel(repo) as T
    }
}