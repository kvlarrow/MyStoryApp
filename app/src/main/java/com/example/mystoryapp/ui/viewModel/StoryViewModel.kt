package com.example.mystoryapp.ui.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mystoryapp.api.response.ListStoryItem
import com.example.mystoryapp.api.response.StoryResponse
import com.example.mystoryapp.api.retrofit.ApiConfig
import com.example.mystoryapp.data.database.Injection
import com.example.mystoryapp.data.database.StoryRepository
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
class StoryViewModel(storyRepository: StoryRepository) : ViewModel() {
    val listStory: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getStory().cachedIn(viewModelScope)
    class StoryViewModelFactory(private val token: String): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
                return StoryViewModel(Injection.provideRepository(token)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }

    }
}