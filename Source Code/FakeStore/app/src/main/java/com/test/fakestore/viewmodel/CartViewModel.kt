package com.test.fakestore.ui.cart

import androidx.lifecycle.*
import com.fakestore.data.local.model.CartItem
import com.fakestore.data.local.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {


    fun addItem(item: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(item)
        }
    }

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> get() = _totalPrice

    fun loadCartItems() {
        _cartItems.value = repository.getCartItems()
        _totalPrice.value = repository.getTotalPrice()
    }

    fun updateQuantity(id: Int, quantity: Int) {
        repository.updateQuantity(id, quantity)
        loadCartItems()
    }

    fun deleteItem(id: Int) {
        repository.deleteItem(id)
        loadCartItems()
    }

    fun clearCart() {
        repository.clearCart()
        loadCartItems()
    }
}
