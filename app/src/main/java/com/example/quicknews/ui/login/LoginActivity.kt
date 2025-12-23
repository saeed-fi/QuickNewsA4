package com.example.quicknews.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quicknews.databinding.ActivityLoginBinding
import com.example.quicknews.ui.main.MainActivity
import com.example.quicknews.utils.PreferencesManager
import com.example.quicknews.utils.ThemeManager

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefsManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        prefsManager = PreferencesManager(this)

        // Apply saved theme
        ThemeManager.applyTheme(this, prefsManager.theme)

        super.onCreate(savedInstanceState)

        // Check if already logged in
        if (prefsManager.isLoggedIn) {
            navigateToMain()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty()) {
                binding.etUsername.error = "Username required"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etPassword.error = "Password required"
                return@setOnClickListener
            }

            // Simple validation (accept any non-empty credentials)
            if (username.length >= 3 && password.length >= 3) {
                prefsManager.isLoggedIn = true
                prefsManager.username = username
                Toast.makeText(this, "Welcome $username!", Toast.LENGTH_SHORT).show()
                navigateToMain()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}