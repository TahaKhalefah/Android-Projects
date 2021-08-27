package com.example.souqadmin.ui.product

import ahmed.adel.sleeem.clowyy.souq.api.RetrofitHandler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.souqadmin.R
import com.example.souqadmin.pojo.*
import com.example.souqadmin.utils.Resource
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private var _productsLiveData = MutableLiveData<Resource<ProductResponse>>(Resource.loading(null))
    val productsLiveData: LiveData<Resource<ProductResponse>> get() = this._productsLiveData

    private var _addProductsLiveData = MutableLiveData<Resource<ProductResponse.Item>>(Resource.loading(null))
    val addProductsLiveData: LiveData<Resource<ProductResponse.Item>> get() = this._addProductsLiveData

    private val _categoryLiveData = MutableLiveData<Resource<CategoryResponse>>()
    val categoryLiveData: LiveData<Resource<CategoryResponse>> get() = this._categoryLiveData

    private var _modifyProductLiveData = MutableLiveData<Resource<ProductResponse.Item>>()
    val modifyProductLiveData: LiveData<Resource<ProductResponse.Item>> get() = _modifyProductLiveData

    private var _deleteProductLiveData = MutableLiveData<Resource<DeleteProductResponce>>()
    val deleteProductLiveData: LiveData<Resource<DeleteProductResponce>> get() = _deleteProductLiveData


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


    fun addProduct(productRequest: ProductRequest){
        viewModelScope.launch {
            _addProductsLiveData.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().addProduct(productRequest)
            if (response.isSuccessful){
                if (response.body() != null) {
                    _addProductsLiveData.value = Resource.success(response.body()!!)
                }
            }else{
                _addProductsLiveData.value = Resource.error(response.code().toString())
            }
        }
    }

    fun getAllCategories() = viewModelScope.launch {
        _categoryLiveData.value = Resource.loading(null)
        _addProductsLiveData.value = Resource.loading(null)
        _modifyProductLiveData.value = Resource.loading(null)
        val response = RetrofitHandler.getItemWebService().getCategory()
        if (response.isSuccessful){
            if(response.body() != null)
                _categoryLiveData.value = Resource.success(response.body()!!);
        }else{
            _categoryLiveData.value = Resource.error(response.errorBody().toString())
        }
    }


    fun modifyProduct(modifyProductRequest: ProductRequest){
        viewModelScope.launch {
            _modifyProductLiveData.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().modifyProduct(modifyProductRequest)
            if (response.isSuccessful){
                if (response.body() != null) {
                    _modifyProductLiveData.value = Resource.success(response.body()!!)
                }
                else
                    _modifyProductLiveData.value = Resource.error("empty");
            }else{
                _modifyProductLiveData.value = Resource.error(response.errorBody().toString());
            }
        }
    }

    fun deleteProduct(deleteReviewRequest: DeleteProductRequest){
        viewModelScope.launch {
            _deleteProductLiveData.value = Resource.loading(null)
            val response = RetrofitHandler.getItemWebService().deleteReview(deleteReviewRequest)
            if (response.isSuccessful)
                _deleteProductLiveData.value = Resource.success(response.body()!!)
            else
                _deleteProductLiveData.value = Resource.error(response.errorBody().toString());
        }
    }

}