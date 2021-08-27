package com.tahadroid.wing.ui.fragments.home.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tahadroid.wing.models.Order
import com.tahadroid.wing.repository.Repository
import kotlinx.coroutines.launch

class OrderBottomSheetViewModel(val repo : Repository) :ViewModel() {
    fun upsert(order:Order)= viewModelScope.launch {
        repo.upsertOrder(order)
    }
    fun deleteAllOrders()=repo.deleteAll()
    fun getAllOrders()=repo.getAllOrders()
}