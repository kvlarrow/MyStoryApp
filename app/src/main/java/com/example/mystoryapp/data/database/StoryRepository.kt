package com.example.mystoryapp.data.database

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.mystoryapp.api.response.ListStoryItem
import com.example.mystoryapp.api.retrofit.ApiService

class StoryRepository(private val apiService: ApiService, private val token: String){
    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPaggingResource(apiService, token)
            }
        ).liveData
    }
}