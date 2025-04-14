package com.test.fakestore.ui.Splashscreen

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.test.fakestore.R
import com.test.fakestore.ui.dashboard.BottomNavigationActivity
import com.test.fakestore.ui.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            delay(2000)
            val prefs: SharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
            val token = prefs.getString("TOKEN", null)

            if (!token.isNullOrEmpty()) {
                startActivity(Intent(this@SplashActivity, BottomNavigationActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }

            finish()
        }
    }
}