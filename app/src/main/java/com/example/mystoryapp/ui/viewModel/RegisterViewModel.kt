package com.example.mystoryapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mystoryapp.api.response.StatusResponse
import com.example.mystoryapp.api.retrofit.ApiConfig
import com.example.mystoryapp.remote.UserPreference
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val preference: UserPreference): ViewModel() {
    companion object {
        private const val TAG = "RegisterViewModel"
    }

    private val _registerStatus = MutableLiveData<StatusResponse>()
    val registerStatus: LiveData<StatusResponse> = _registerStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val error = MutableLiveData("")

    fun postRegister(name: String, email: String, password: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().postRegister(name, email, password)
        client.enqueue(object : Callback<StatusResponse>{
            override fun onResponse(
                call: Call<StatusResponse>,
                response: Response<StatusResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _registerStatus.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    response.errorBody()?.let {
                        val errorResponse = JSONObject(it.string())
                        val errorMessage = errorResponse.getString("message")
                        error.postValue(errorMessage)
                    }
                }
            }

            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message.toString()}")
                _isLoading.value = false
            }
        })
    }
}