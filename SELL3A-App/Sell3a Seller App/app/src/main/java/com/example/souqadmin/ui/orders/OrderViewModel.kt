package com.example.souqadmin.ui.orders

import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.souqadmin.pojo.OrderResponseItem
import com.example.souqadmin.utils.Resource
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    private val _orders = MutableLiveData<Resource<List<OrderResponseItem>>>()
    val orders: MutableLiveData<Resource<List<OrderResponseItem>>> get() = _orders


    fun getOrderByCompanyName(companyName : String) = viewModelScope.launch {
        _orders.value = Resource.loading(null)
        val response = RetrofitHandler.getItemWebService().getOrdersByCompanyName(companyName)
        if (response.isSuccessful) {
            if (response.body() != null)
                _orders.value = Resource.success(response.body()!!)
        } else {
            _orders.value = Resource.error(response.errorBody().toString())
        }
    }

}