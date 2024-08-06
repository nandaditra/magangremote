 package com.example.magangremote.ui.auth.lupaPassword

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.magangremote.databinding.ActivityLupaPasswordBinding
import com.example.magangremote.ui.auth.AuthActivity
import com.example.magangremote.ui.auth.AuthViewModel
import com.google.firebase.auth.FirebaseAuth


 class LupaPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLupaPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val model = ViewModelProvider(this)[LupaPasswordViewModel::class.java]
        binding.btnForgetPassword.setOnClickListener { forgotPassword(model) }
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

     private fun forgotPassword(model: LupaPasswordViewModel) {
         val email = binding.inputEmailLogin.text.trim().toString()
         if(email.isEmpty()) {
             binding.inputEmailLogin.error = "Email tidak boleh kosong"
         } else {
             model.forgotPassword(email)
             model.status.observe(this@LupaPasswordActivity){
                 if(it == true) {
                     Toast.makeText(this, "Check email to reset password", Toast.LENGTH_SHORT).show()
                     startActivity(Intent(this, AuthActivity::class.java))
                 } else {
                     Toast.makeText(this, "Failed to reset password", Toast.LENGTH_SHORT).show()
                 }
             }
         }
     }
 }