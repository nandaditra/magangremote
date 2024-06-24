 package com.example.magangremote.ui.lupaPassword

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.magangremote.databinding.ActivityLupaPasswordBinding


 class LupaPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLupaPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

     override fun onContextItemSelected(item: MenuItem): Boolean {
         when (item.itemId) {
             android.R.id.home -> {
                 finish()
                 return true
             }
         }
         return super.onContextItemSelected(item)
     }
 }