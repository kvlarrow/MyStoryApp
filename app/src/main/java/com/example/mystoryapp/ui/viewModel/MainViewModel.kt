package com.example.mystoryapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mystoryapp.remote.UserModel
import com.example.mystoryapp.remote.UserPreference
import kotlinx.coroutines.launch

class MainViewModel(private val preferences: UserPreference): ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return preferences.getUser().asLiveData()
    }
    fun logout() {
        viewModelScope.launch {
            preferences.logout()
        }
    }
}