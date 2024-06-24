package com.example.magangremote.ui.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.magangremote.R

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}