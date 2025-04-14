package com.test.fakestore.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels // ✅ Required import for viewModels()
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.test.fakestore.R
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import com.test.fakestore.ui.dashboard.BottomNavigationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameField = findViewById<EditText>(R.id.etUsername)
        val passwordField = findViewById<EditText>(R.id.etPassword)
        val loginBtn = findViewById<Button>(R.id.btnLogin)

        // Set default background (inactive)
        loginBtn.isEnabled = false
        loginBtn.setBackgroundResource(R.drawable.btn_custom_hint)

        // Watch text changes for validation
        val inputWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val username = usernameField.text.toString().trim()
                val password = passwordField.text.toString().trim()

                if (username.isNotEmpty() && password.isNotEmpty()) {
                    loginBtn.isEnabled = true
                    loginBtn.setBackgroundResource(R.drawable.btn_custom_black)
                } else {
                    loginBtn.isEnabled = false
                    loginBtn.setBackgroundResource(R.drawable.btn_custom_hint)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        usernameField.addTextChangedListener(inputWatcher)
        passwordField.addTextChangedListener(inputWatcher)

        // Observe login result
        viewModel.loginResult.observe(this) { result ->
            result
                .onSuccess { token ->
                    val prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                    prefs.edit().putString("TOKEN", "Bearer $token").apply()
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, BottomNavigationActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                .onFailure {
                    Toast.makeText(this, "Login Failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

        loginBtn.setOnClickListener {
            val username = usernameField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(username, password)
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}