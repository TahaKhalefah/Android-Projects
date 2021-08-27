package com.tahadroid.wing.ui.fragments.home.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tahadroid.wing.repository.Repository


class OrderBottomSheetViewModelFactory(
    val repo: Repository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrderBottomSheetViewModel(repo) as T
    }
}