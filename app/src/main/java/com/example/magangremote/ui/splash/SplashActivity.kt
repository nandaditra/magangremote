package com.example.magangremote.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import com.example.magangremote.R
import com.example.magangremote.ui.auth.AuthActivity
import com.example.magangremote.ui.home.HomeActivity
import com.example.magangremote.ui.notification.NotificationActivity
import com.example.magangremote.ui.profile.ProfileActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        android.os.Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, AuthActivity::class.java )
            startActivity(intent)
            finish()
        }, 3000)
    }
}