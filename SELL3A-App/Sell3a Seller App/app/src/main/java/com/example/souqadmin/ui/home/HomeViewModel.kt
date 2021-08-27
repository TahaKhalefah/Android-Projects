package com.example.souqadmin.ui.home

import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.souqadmin.pojo.OrderResponseItem
import com.example.souqadmin.pojo.ProductResponse
import com.example.souqadmin.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {


    private var _productsLiveData = MutableLiveData<Resource<ProductResponse>>(Resource.loading(null))
    val productsLiveData: LiveData<Resource<ProductResponse>> get() = this._productsLiveData


    private var _soldOutLiveData = MutableLiveData<Resource<ProductResponse>>(Resource.loading(null))
    val soldOutLiveData: LiveData<Resource<ProductResponse>> get() = this._soldOutLiveData

    private var _lowInStackLiveData = MutableLiveData<Resource<ProductResponse>>(Resource.loading(null))
    val lowInStackLiveData: LiveData<Resource<ProductResponse>> get() = this._lowInStackLiveData


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


    fun getSoldOutItemsByCount(companyName: String , count : String) {
        viewModelScope.launch {
            _soldOutLiveData.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().getItemsByCount(companyName , count)
            if(response.isSuccessful){
                if (response.body() != null)
                    if (count != "0") {
                        _lowInStackLiveData.value = Resource.success(response.body()!!)
                    }else if (count == "0"){
                        _soldOutLiveData.value = Resource.success(response.body()!!)
                    }
            }else
                _soldOutLiveData.value = Resource.error(response.errorBody().toString())
        }
    }


    fun getItemsByCompanyName(companyName: String) {
        viewModelScope.launch {
            _productsLiveData.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().getItemsByCompanyName(companyName)
            if(response.isSuccessful){
                if (response.body() != null)
                    _productsLiveData.value = Resource.success(response.body()!!)
            }else
                _productsLiveData.value = Resource.error(response.errorBody().toString())
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}