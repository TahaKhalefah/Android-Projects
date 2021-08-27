package com.example.souqadmin.ui.orders

import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.souqadmin.pojo.ItemResponse
import com.example.souqadmin.pojo.ModifyOrderState
import com.example.souqadmin.pojo.OrderResponse
import com.example.souqadmin.pojo.OrderResponseItem
import com.example.souqadmin.utils.Resource
import kotlinx.coroutines.launch

class OrderDetailsViewModel: ViewModel() {

    private val _item = MutableLiveData<Resource<ItemResponse>>()
    val item: MutableLiveData<Resource<ItemResponse>> get() = _item

    private var _modifyStateLiveData = MutableLiveData<Resource<OrderResponseItem>>()
    val modifyStateLiveData: LiveData<Resource<OrderResponseItem>> get() = _modifyStateLiveData


    fun getItemsById(id: String) = viewModelScope.launch {
        _item.value = Resource.loading(null)
        val response = RetrofitHandler.getItemWebService().getItemsById(id)
        if (response.isSuccessful) {
            if (response.body() != null)
                _item.value = Resource.success(response.body()!!)
        } else {
            _item.value = Resource.error(response.errorBody().toString())
        }
    }

    fun modifyState(modifyOrderState: ModifyOrderState){
        viewModelScope.launch {
            _modifyStateLiveData.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().modifyState(modifyOrderState)
            if (response.isSuccessful){
                if (response.body() != null) {
                    _modifyStateLiveData.value = Resource.success(response.body()!!)
                }
                else
                    _modifyStateLiveData.value = Resource.error("empty");
            }else{
                _modifyStateLiveData.value = Resource.error(response.errorBody().toString());
            }
        }
    }
}