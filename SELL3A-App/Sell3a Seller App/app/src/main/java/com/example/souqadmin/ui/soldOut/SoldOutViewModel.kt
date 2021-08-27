package com.example.souqadmin.ui.soldOut

import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.souqadmin.pojo.ProductResponse
import com.example.souqadmin.utils.Resource
import kotlinx.coroutines.launch

class SoldOutViewModel : ViewModel() {
    private var _productsLiveData = MutableLiveData<Resource<ProductResponse>>(Resource.loading(null))
    val productsLiveData: LiveData<Resource<ProductResponse>> get() = this._productsLiveData


    fun getItemsByCount(companyName: String , count : String) {
        viewModelScope.launch {
            _productsLiveData.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().getItemsByCount(companyName , count)
            if(response.isSuccessful){
                if (response.body() != null)
                    _productsLiveData.value = Resource.success(response.body()!!)
            }else
                _productsLiveData.value = Resource.error(response.errorBody().toString())
        }
    }


}