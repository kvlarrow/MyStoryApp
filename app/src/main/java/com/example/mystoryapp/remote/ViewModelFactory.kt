package com.example.mystoryapp.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.ui.viewModel.AddStoryViewModel
import com.example.mystoryapp.ui.viewModel.LoginViewModel
import com.example.mystoryapp.ui.viewModel.MainViewModel
import com.example.mystoryapp.ui.viewModel.MapsViewModel
import com.example.mystoryapp.ui.viewModel.RegisterViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val preference: UserPreference): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(preference) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(preference) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(preference) as T
            }
            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                AddStoryViewModel(preference) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(preference) as T
            }
            else -> throw IllegalArgumentException("There is no ViewModel called: " + modelClass.name)
        }
    }
}