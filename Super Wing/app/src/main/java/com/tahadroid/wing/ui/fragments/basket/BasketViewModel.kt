package com.tahadroid.wing.ui.fragments.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tahadroid.wing.models.Order
import com.tahadroid.wing.repository.Repository
import kotlinx.coroutines.launch

class BasketViewModel(val repo : Repository) :ViewModel() {
    fun getAll() = repo.getAllOrders()
    fun deleteOrder(order:Order) =viewModelScope.launch {
        repo.deleteOrder(order)
    }
    fun deleteAll() =repo.deleteAll()
}