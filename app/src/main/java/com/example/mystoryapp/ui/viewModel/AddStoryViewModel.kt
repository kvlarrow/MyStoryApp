package com.example.mystoryapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.mystoryapp.api.response.StatusResponse
import com.example.mystoryapp.api.retrofit.ApiConfig
import com.example.mystoryapp.remote.UserModel
import com.example.mystoryapp.remote.UserPreference
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStoryViewModel(private val preferences: UserPreference): ViewModel() {
    companion object {
        private const val TAG = "AddStoryViewModel"
    }

    val latitude = MutableLiveData(0.0)
    val longitude = MutableLiveData(0.0)

    private val _postStatus = MutableLiveData<StatusResponse>()
    val postStatus: LiveData<StatusResponse> = _postStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val error = MutableLiveData("")

    fun getUser(): LiveData<UserModel> {
        return preferences.getUser().asLiveData()
    }

    fun postNewStoryWithLocation(token: String, storyImage: MultipartBody.Part, description: RequestBody, lat: Double, lng: Double) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postStory(token, storyImage, description, lat, lng)
        client.enqueue(object : Callback<StatusResponse> {
            override fun onResponse(call: Call<StatusResponse>, response: Response<StatusResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _postStatus.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    response.errorBody()?.let {
                        val errorResponse = JSONObject(it.string())
                        val errorMessages = errorResponse.getString("message")
                        error.postValue(errorMessages)
                    }
                }
            }
            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                Log.e(TAG, "onFailure (OF): ${t.message.toString()}")
                _isLoading.value = false
            }
        })
    }

    fun postNewStory(token: String, storyImage: MultipartBody.Part, description: RequestBody) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postStory(token, storyImage, description, 0.0, 0.0)
        client.enqueue(object : Callback<StatusResponse> {
            override fun onResponse(call: Call<StatusResponse>, response: Response<StatusResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _postStatus.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    response.errorBody()?.let {
                        val errorResponse = JSONObject(it.string())
                        val errorMessages = errorResponse.getString("message")
                        error.postValue(errorMessages)
                    }
                }
            }
            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                Log.e(TAG, "onFailure (OF): ${t.message.toString()}")
                _isLoading.value = false
            }
        })
    }
}