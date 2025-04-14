package com.test.fakestore.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.test.fakestore.data.network.ApiService
import com.test.fakestore.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

// Use the @HiltViewModel annotation
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val apiService: ApiService // ApiService will be injected by Hilt
) : ViewModel() {

    fun getUserProfile(userId: Int) = liveData(Dispatchers.IO) {
        try {
            val user: User = apiService.getUserById(userId)
            emit(user)
        } catch (exception: Exception) {
            emit(null)
        }
    }
}
