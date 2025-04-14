package com.test.fakestore.data.repository

import com.test.fakestore.data.model.Product
import com.test.fakestore.data.network.ApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun fetchProducts(): List<Product> = api.getAllProducts()

    suspend fun fetchCategories(): List<String> = api.getAllCategories()

    suspend fun fetchProductsByCategory(category: String): List<Product> =
        api.getProductsByCategory(category)
}
