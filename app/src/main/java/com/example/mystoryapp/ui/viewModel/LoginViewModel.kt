package com.example.mystoryapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystoryapp.api.response.LoginResponse
import com.example.mystoryapp.api.retrofit.ApiConfig
import com.example.mystoryapp.remote.UserModel
import com.example.mystoryapp.remote.UserPreference
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class LoginViewModel(private val preference: UserPreference) : ViewModel() {
    companion object {
        private const val TAG = "LoginViewModel"
    }

    private val _loginStatus = MutableLiveData<LoginResponse>()
    val loginStatus: LiveData<LoginResponse> = _loginStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val error = MutableLiveData("")

    fun postLogin(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postLogin(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _loginStatus.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    response.errorBody()?.let {
                        val errorResponse = JSONObject(it.string())
                        val errorMessage = errorResponse.getString("message")
                        error.postValue(errorMessage)
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "onFailure (OF): ${t.message.toString()}")
                _isLoading.value = false
            }

        })
    }

    fun setUserPreference(userId: String, name: String, token: String) {
        viewModelScope.launch {
            preference.saveUserPref(
                UserModel(userId, name, token, true)
            )
        }
    }
}