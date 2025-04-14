package com.test.fakestore.di

import android.content.Context
import androidx.room.Room
import com.test.fakestore.data.api.FakeStoreApiService
import com.test.fakestore.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // BASE URL
    @Provides
    fun provideBaseUrl(): String = "https://fakestoreapi.com/"

    // RETROFIT
    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // API Services
    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): FakeStoreApiService =
        retrofit.create(FakeStoreApiService::class.java)

    @Provides
    @Singleton
    fun provideProductApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

}
