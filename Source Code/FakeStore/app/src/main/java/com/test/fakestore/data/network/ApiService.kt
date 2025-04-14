package com.test.fakestore.data.network

import com.test.fakestore.data.model.Product
import com.test.fakestore.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("products")
    suspend fun getAllProducts(): List<Product>

    @GET("products/categories")
    suspend fun getAllCategories(): List<String>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): List<Product>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): User

}
