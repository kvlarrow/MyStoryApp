package com.example.mystoryapp.data.database

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mystoryapp.api.response.ListStoryItem
import com.example.mystoryapp.api.retrofit.ApiService
import java.lang.Exception

class StoryPaggingResource(private val apiService: ApiService, val token: String): PagingSource<Int, ListStoryItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responData = apiService.getStoryList(position, params.loadSize, token).listStory
            LoadResult.Page(
                data = responData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position -1,
                nextKey = if (responData.isEmpty()) null else position +1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}