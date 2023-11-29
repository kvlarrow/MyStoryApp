package com.example.mystoryapp.data.database

import com.example.mystoryapp.api.retrofit.ApiConfig

object Injection {
    fun provideRepository(token: String): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService, token)
    }
}