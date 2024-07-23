 package com.example.magangremote.ui.auth.lupaPassword

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.magangremote.databinding.ActivityLupaPasswordBinding
import com.example.magangremote.ui.auth.AuthActivity
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
        
        binding.btnForgetPassword.setOnClickListener { forgotPassword() }
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

     private fun forgotPassword() {
         var email = binding.inputEmailLogin.text.trim().toString()
         if(email.isEmpty()) {
             binding.inputEmailLogin.error = "Email tidak boleh kosong"
         } else {
             firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener{
                 if (it.isSuccessful){
                     Toast.makeText(this, "Check email to reset password", Toast.LENGTH_SHORT).show()
                     startActivity(Intent(this, AuthActivity::class.java))
                 }else{
                     Toast.makeText(this, "Failed to reset password", Toast.LENGTH_SHORT).show()
                 }
             }
         }
     }
 }