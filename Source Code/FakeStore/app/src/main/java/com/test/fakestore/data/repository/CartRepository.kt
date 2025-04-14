package com.fakestore.data.local.repository
import com.fakestore.data.local.db.SQLiteHelper
import com.fakestore.data.local.model.CartItem

class CartRepository(private val dbHelper: SQLiteHelper) {

    fun addItem(item: CartItem) {
        dbHelper.insertItem(item)
    }

    fun getCartItems(): List<CartItem> {
        return dbHelper.getAllItems()
    }

    fun getTotalPrice(): Double {
        return dbHelper.getTotalPrice()
    }

    fun updateQuantity(id: Int, quantity: Int) {
        dbHelper.updateItemQuantity(id, quantity)
    }

    fun deleteItem(id: Int) {
        dbHelper.deleteItem(id)
    }

    fun clearCart() {
        dbHelper.clearCart()
    }
}
