package com.example.mystoryapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mystoryapp.R
import com.example.mystoryapp.databinding.ActivityMainBinding
import com.example.mystoryapp.remote.UserPreference
import com.example.mystoryapp.remote.ViewModelFactory
import com.example.mystoryapp.ui.fragment.StoryFragment
import com.example.mystoryapp.ui.viewModel.MainViewModel


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel()
        setAction()
        setAppbar()

    }

    private fun setAction() {
        binding.addFab.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setAppbar() {
        binding.iconLogout.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.mn_logout -> {
                    Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                    mainViewModel.logout()
                    true
                }
                R.id.mn_maps -> {
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun viewModel() {
        mainViewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]
        mainViewModel.getUser().observe(this) {
            token = "Bearer ${it.token}"
            getToken()
        }
        mainViewModel.getUser().observe(this) {
            if (it.isLogin) {
                supportActionBar?.show()
                binding.iconLogout.title = "Hallo, ${it.name}"
                fragments(StoryFragment())
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun getToken() = token

    private fun fragments(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}