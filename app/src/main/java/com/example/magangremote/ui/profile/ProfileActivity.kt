package com.example.magangremote.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.magangremote.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}