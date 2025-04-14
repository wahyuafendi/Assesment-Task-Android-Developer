package com.test.fakestore.data.model

data class User(
    val id: Int,
    val email: String,
    val username: String,
    val name: Name,
    val phone: String,
    val address: Address
)

data class Name(
    val firstname: String,
    val lastname: String
)

data class Address(
    val city: String,
    val street: String,
    val number: Int,
    val zipcode: String,
    val geolocation: GeoLocation
)

data class GeoLocation(
    val lat: String,
    val long: String
)
