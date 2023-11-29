package com.example.mystoryapp.ui.activity

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.R
import com.example.mystoryapp.databinding.ActivityRegisterBinding
import com.example.mystoryapp.remote.UserPreference
import com.example.mystoryapp.remote.ViewModelFactory
import com.example.mystoryapp.ui.viewModel.RegisterViewModel
import java.util.prefs.Preferences

private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "settings")
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()

        viewModel()
        action()
        setAnimation()
    }

    private fun viewModel() {
        registerViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(    dataStore))
        )[RegisterViewModel::class.java]

        registerViewModel.registerStatus.observe(this) {
            if (!it.error) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        registerViewModel.error.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun action() {
        binding.tvSignin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.costumButton.setOnClickListener {
            val name = binding.edRegistName.text.toString()
            val email = binding.edRegistEmail.text.toString()
            val password = binding.edRegistPassword.text.toString()
            when {
                name.isEmpty() -> {
                    binding.edRegistName.error = getString(R.string.empty_name_field)
                }
                email.isEmpty() -> {
                    binding.edRegistEmail.error = getString(R.string.empty_email_field)
                }
                password.isEmpty() -> {
                    binding.edRegistPassword.error = getString(R.string.empty_password_field)
                }
                else -> {
                    registerViewModel.postRegister(name, email, password)
                }
            }
        }
    }

    private fun setAnimation() {
        ObjectAnimator.ofFloat(binding.textView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}