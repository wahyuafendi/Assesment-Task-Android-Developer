package com.test.fakestore.viewmodel

import androidx.lifecycle.*
import com.test.fakestore.data.model.Product
import com.test.fakestore.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    fun getAllProducts() {
        viewModelScope.launch {
            _products.value = repository.fetchProducts()
        }
    }

    fun getAllCategories() {
        viewModelScope.launch {
            _categories.value = repository.fetchCategories()
        }
    }

    fun getProductsByCategory(category: String) {
        viewModelScope.launch {
            _products.value = repository.fetchProductsByCategory(category)
        }
    }
}
