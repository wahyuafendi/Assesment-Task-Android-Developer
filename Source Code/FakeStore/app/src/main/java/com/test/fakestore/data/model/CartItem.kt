package com.fakestore.data.local.model

import java.io.Serializable

data class CartItem(
    val id: Int,
    val title: String,
    val price: Double,
    val image: String,
    var quantity: Int
):Serializable