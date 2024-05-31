package com.appinventiv.mvvmunittesting.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appinventiv.mvvmunittesting.api.NetworkResult
import com.appinventiv.mvvmunittesting.models.ProductListItem
import com.appinventiv.mvvmunittesting.repository.ProductRepository
import kotlinx.coroutines.launch

class MainViewModel(val repository: ProductRepository) : ViewModel() {
    private val _products = MutableLiveData<NetworkResult<List<ProductListItem>>>()
    val products : LiveData<NetworkResult<List<ProductListItem>>>
        get() = _products

    fun getProducts() {
        viewModelScope.launch {
            val result = repository.getProducts()
            _products.postValue(result)
        }
    }
}