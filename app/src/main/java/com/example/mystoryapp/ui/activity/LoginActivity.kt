package com.example.mystoryapp.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
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
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.R
import com.example.mystoryapp.databinding.ActivityLoginBinding
import com.example.mystoryapp.remote.UserPreference
import com.example.mystoryapp.remote.ViewModelFactory
import com.example.mystoryapp.ui.viewModel.LoginViewModel


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        setAction()
        setViewModel()
        setAnimation()
    }

    private fun setView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    @SuppressLint("Recycle")
    private fun setAnimation() {
        ObjectAnimator.ofFloat(binding.textView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val title = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 1f).setDuration(3000)
        val email = ObjectAnimator.ofFloat(binding.emailField, View.ALPHA, 1f).setDuration(3000)
        val password = ObjectAnimator.ofFloat(binding.passwordField, View.ALPHA, 1f).setDuration(3000)
        val signIn = ObjectAnimator.ofFloat(binding.tvSignup, View.ALPHA, 1f).setDuration(3000)
        val signUpSuggest = ObjectAnimator.ofFloat(binding.textView3, View.ALPHA, 1f).setDuration(3000)

        AnimatorSet().apply {
            playSequentially(title, email, password, signIn, signUpSuggest)
            startDelay = 500
            start()
        }
    }

    private fun setViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.let { vm -> 
            vm.loginStatus.observe(this){
                loginViewModel.setUserPreference(
                    it.loginResult!!.userId,
                    it.loginResult.name,
                    it.loginResult.token
                )
            }
        }
        loginViewModel.loginStatus.observe(this){
            if (!it.error!!){
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        loginViewModel.error.observe(this){
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
        loginViewModel.isLoading.observe(this){
            showLoading(it)
        }
    }

    private fun setAction(){
        binding.tvSignup.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.costumButton.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            when {
                email.isEmpty() -> {
                    binding.edEmail.error = getString(R.string.empty_email_field)
                }
                password.isEmpty() -> {
                    binding.edPassword.error = getString(R.string.empty_password_field)
                }
                else -> {
                    loginViewModel.postLogin(email, password)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }
}