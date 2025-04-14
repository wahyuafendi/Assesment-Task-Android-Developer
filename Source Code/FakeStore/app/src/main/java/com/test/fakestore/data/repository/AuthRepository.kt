package com.test.fakestore.data.repository

import com.test.fakestore.data.api.FakeStoreApiService
import com.test.fakestore.data.model.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: FakeStoreApiService
) {
    suspend fun login(username: String, password: String): Response<LoginResponse> {
        return api.login(username, password)
    }
}
